(ns bag-of-words.core
  (:gen-class))

(def test-text
  "Results were bad and the boss was bad. Neverthess our competitors were worse."
  )

(def test-dict
  #{"bad","worse","horrible"}
  )

(defn -main
  nil)

(defn filter-special-chars-away [text]
  (apply str
         (remove #(#{\.,\,} %) text))
  )

(defn split-to-words [text]
  (clojure.string/split
   (filter-special-chars-away text)
   #" ")
  )


(defn word-frequencies [text]
  (frequencies (split-to-words text))
  )

(defn dictionary-word-frequencies [dict text]
  (let [in-dictionary?
        (fn [word]
          (contains? dict word))]
    (frequencies
     (filter in-dictionary?
             (split-to-words text))))
  )

