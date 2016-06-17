(ns bag-of-words.webdriver-scrape
  (:require [clj-webdriver.taxi :as t]
            [net.cgrand.enlive-html :as e]
            [clojure.string :as str]))

(defn title-column [thead-th]
  (read-string
    (get-in
      thead-th
      [:attrs :data-column])))

(defn title-content [thead-th]
  (first
    (:content
      (first
        (:content
          thead-th)))))

(defn title-data [thead-th]
  {:column-num (title-column thead-th)
   :content (title-content thead-th)})

(defn thead-cols [t-head]
  (reduce
    #(conj %1 (title-data %2))
    [] t-head))

(comment
  (def thead-data
    (e/select
      (e/html-snippet
        (t/html (t/find-element {:tag :thead})))
      [:th])))

(defn col-index [col-name head-map]
  (:column-num
    (first
      (filter #(= (:content %) col-name) head-map))))

(comment
(def tbody-tr-data
  (e/select
    (e/html-snippet
      (t/html (t/find-element {:tag :tbody
                               :id  "data"})))
    [:tr]))
)

(defn col-content [tbody-tr col-name thead-col-map]
  (let [col-content-data
        (first
          (:content
            (nth
              (:content tbody-tr)
              (col-index col-name thead-col-map))))]
    (if (string? col-content-data)
      col-content-data
      (first (:content col-content-data)))))

(defn col-href [tbody-tr col-name thead-col-map]
  (let [col-content-data
        (first
          (:content
            (nth
              (:content tbody-tr)
              (col-index col-name thead-col-map))))]
    (get-in col-content-data [:attrs :href])))

(defn url-params [url]
  (into {} (for [[_ k v] (re-seq #"([^&=]+)=([^&]+)" (last (str/split url #"\?")))]
             [(keyword k) v])))

(defn table-data [thead-col-map tbody-tr-col]
  (reduce #(conj %1
                 {:timesteamp (col-content %2 "Date (CET)" thead-col-map)
                  :company (col-content %2 "Company" thead-col-map)
                  :category (col-content %2 "Category" thead-col-map)
                  :subject (col-content %2 "Subject" thead-col-map)
                  :link (col-href %2 "Subject" thead-col-map)
                  :link-id (:disclosureId (url-params (col-href %2 "Subject" thead-col-map)))})
          []
          tbody-tr-col))

(defn scrape! [url]
  (do
    (t/set-driver! {:browser :firefox})
    (t/to url)
    (t/wait-until (and
                    (t/exists? {:tag :tbody
                                :id  "data"})
                    (t/exists? {:tag :thead})))
    (clojure.pprint/pprint
      (table-data
        (thead-cols
          (e/select
            (e/html-snippet
              (t/html (t/find-element {:tag :thead})))
            [:th]))
        (e/select
          (e/html-snippet
            (t/html (t/find-element {:tag :tbody
                                     :id  "data"})))
          [:tr])))
    (t/close)))