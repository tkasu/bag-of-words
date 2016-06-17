(ns bag-of-words.core
  (:gen-class)
  (:require [bag-of-words.database :as db]
            [bag-of-words.files :as files]
            [bag-of-words.text-refactoring :as text]
            [bag-of-words.html-parse :as html]
            [clojure.java.jdbc :as j]
            [clojure.string :as str]))

;Function used by 'dict-data-to-db-helper'
;See comments in 'insert-grouped-dict-data-to-db'
(defn insert-dict-group-to-db [group]
  (j/insert!
    db/db
    :dict_group
    {:head_word (get group 0)}))

;Helper function for 'insert-dict-words-to-db'
;See comments in 'insert-grouped-dict-data-to-db'
(defn dict-words-to-db-helper [dict-id group-id group acc]
  (if (or
        (empty? (first group))
        (= (first group) ""))
    acc
    (let [new-acc
          (conj
            acc
            {:dict_id dict-id
             :word (.toLowerCase (first group))
             :group_id group-id})]
      (recur dict-id group-id (rest group) new-acc))))

;Inserts group data and dict-words to db
;See comments in 'insert-grouped-dict-data-to-db'
(defn insert-dict-words-to-db [dict-id group-data]
  (for [group group-data]
    (let [group-id (get (first (insert-dict-group-to-db group)) :id)
          group-words (dict-words-to-db-helper dict-id group-id group ())]
      (apply
        j/insert!
        db/db
        :dict_words
        group-words))))

;Insert grouped dict data to db
;Together with 'insert-dict-words-to-db' & 'insert-dict-group-td-db' this function
;adds groupped dict data to 3 SQL-tables, :dictionary, :dict_group & :dict_words
;See sql/
(defn insert-grouped-dict-data-to-db [dict-name group-data]
  (insert-dict-words-to-db
    (get (first (j/insert! db/db :dictionary {:name dict-name})) :id)
    group-data))

;Function to import general information about the information release to DB's release_info -table
(defn insert-release-to-db [release]
  (j/insert!
   db/db
   :release_info
   {:company_name (get release 2)
    :publish_date (get release 1)}))

;helper for 'insert-release-text-to-db'
(defn release-text-data-helper [release-id release-words acc_nro acc]
  (if (empty? release-words)
    acc
    (let [new_acc (conj
                    acc
                    {:release_id release-id
                     :order_nro acc_nro
                     :word (if (< (count (first release-words)) 255)
                             (.toLowerCase (first release-words))
                             ("ERRORPSQL"))})]
      (recur release-id (rest release-words) (inc acc_nro) new_acc))))

;Function that inserts release text to DB's release_text table as single words
(defn insert-release-text-to-db [release-id release-words]
  (let [word-db-data (release-text-data-helper release-id release-words 1 ())]
  (if (empty? word-db-data)
      (println (str "Release " release-id " empty!"))
      (do
        (apply
         j/insert!
         db/db
         :release_text
         word-db-data)
        (println (str "Release " release-id " imported!"))))))

;Function that inserts release info & text to DB
(defn insert-release-data-to-db [release-data]
  (for [release release-data]
    (let [release-id
          (get (first (insert-release-to-db release)) :id)]
      (insert-release-text-to-db release-id
                                (text/format-and-split-text-to-word (html/body-paragraphs-as-text (html/parse-html-url (get release 5))))))))

