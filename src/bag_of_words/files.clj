(ns bag-of-words.files
  (:require [clojure-csv.core :as csv]
            [clojure.java.io :as io]
            [net.cgrand.enlive-html :as e])
  )


(defn read-text [text-file]
  ;The first column is assumed to be name-column and therefore excluded
  (rest
   (with-open [rdr (io/reader (.getPath (io/resource text-file)))]
     (doall
      (csv/parse-csv rdr :delimiter \,)))))

(defn read-xml [xml-file tag-key]
  (let [xml-res
        (with-open [rdr (io/reader (.getPath (io/resource xml-file)))]
          (e/xml-resource rdr))]
    (first (apply :content (e/select xml-res [tag-key])))))


;TO-DO
(defn write-to-file [file text]
  )

;TO-DO
(defn write-csv-to-file [file text]
  )