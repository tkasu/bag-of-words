(ns bag-of-words.dictionaries
  )

(defn formatted-dict [dict-raw]
  (set
    (map clojure.string/lower-case
     (map clojure.string/trim dict-raw))))

;Add your own dictionaries here
;Current dictionaries are downloaded from:
;http://www3.nd.edu/~mcdonald/Word_Lists.html
;And added to bag-of-words/resources folder

(def dict-un
  (formatted-dict
    (with-open [rdr (clojure.java.io/reader (.getPath (clojure.java.io/resource "LoughranMcDonald_Uncertainty.csv")))]
     (doall (line-seq rdr)))))

(def dict-neg
  (formatted-dict
    (with-open [rdr (clojure.java.io/reader (.getPath (clojure.java.io/resource "LoughranMcDonald_Negative.csv")))]
     (doall (line-seq rdr)))))



