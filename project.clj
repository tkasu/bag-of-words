(defproject bag-of-words "0.1.0-SNAPSHOT"
  :description "TO:DO Simple Bag-of-Words program for sentiment analysis"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/java.jdbc "0.3.6"]
                 [postgresql/postgresql "8.4-702.jdbc4"]
                 [clojure-csv/clojure-csv "2.0.1"]
                 [enlive "1.1.5"]
                 [clj-tagsoup/clj-tagsoup "0.3.0"]]
  :main ^:skip-aot bag-of-words.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
