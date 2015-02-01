(ns bag-of-words.dictionaries
  )

;Add your own dictionaries here
;Current dictionaries are downloaded from:
;http://www3.nd.edu/~mcdonald/Word_Lists.html
;And added to bag-of-words/resources folder

(def dict-files
  ["LoughranMcDonald_Uncertainty.csv"
   "LoughranMcDonald_Negative.csv"
   "LoughranMcDonald_Positive.csv"])

;Tools to read and format dictionaries

(defn formatted-dict [dict-raw]
  (set
    (map clojure.string/lower-case
     (map clojure.string/trim dict-raw))))

(defn read-dict [dict-file]
  (formatted-dict
    (with-open [rdr (clojure.java.io/reader (.getPath (clojure.java.io/resource dict-file)))]
     (doall (line-seq rdr)))))





