(ns bag-of-words.text-refactoring
  (:gen-class))

;New method to filter special numbers, special chars and line/tab spacing away
;and to split text to word using regEx
(defn format-and-split-text-to-word [text]
  (re-seq #"[a-zA-ZäöüÄÖÜ]{2,}" text))