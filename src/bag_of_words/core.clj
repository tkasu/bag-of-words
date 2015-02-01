(ns bag-of-words.core
  (:gen-class)
  (:require [bag-of-words.dictionaries :refer :all]))

(def test-text2
  "almost approximate bad approximate dump")

(defn filter-special-chars-away [text]
  (apply str
         (remove #(#{\.,\,} %) text)))

(defn split-to-words [text]
  (clojure.string/split
   (filter-special-chars-away text)
   #" "))

(defn words-in-dictionary [dict text]
  (let [in-dictionary?
        (fn [word]
          (contains? dict word))]
    (filter in-dictionary?
             (split-to-words (clojure.string/lower-case text)))))

(defn word-frequencies [text]
  (frequencies (split-to-words text)))

(defn dictionary-word-frequencies [dict text]
    (frequencies
     (words-in-dictionary dict text)))

;TEST FUNCTIONALITY
(defn -main [args]
  (println
    (for [dict-path dict-files]
      (str "Dict file: " dict-path " "
           "Words in dict " (count (words-in-dictionary (read-dict dict-path) args)) " "
           "Words: " (dictionary-word-frequencies (read-dict dict-path) args) \newline))))

