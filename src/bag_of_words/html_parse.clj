(ns bag-of-words.html-parse
  (:gen-class)
  (:require [net.cgrand.enlive-html :as e]))

(defn parse-html-url [url]
  (e/html-resource (java.net.URL. url)))

(defn body-paragraphs-as-text [html]
  (apply str
         (map e/text
              (e/select html [:body #{:p :pre}]))))
