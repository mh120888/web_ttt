(defproject web-ttt "0.1.0-SNAPSHOT"
  :description "A web-based tic tac toe game"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [matts-clojure-ttt "0.1.0-SNAPSHOT"]
                 [com.github.mh120888/javaserver "1.0-SNAPSHOT"]]
  :plugins [[speclj "3.3.0"]]
  :test-paths ["spec"]
  :main ^:skip-aot web-ttt.core
  :target-path "target/%s"
  :profiles { :dev {:dependencies [[speclj "3.3.0"]]}
              :uberjar {:aot :all}})
