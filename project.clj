(defproject bag-of-words "0.1.0-SNAPSHOT"
  :description "TO:DO Simple Bag-of-Words program for sentiment analysis"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :main ^:skip-aot bag-of-words.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
