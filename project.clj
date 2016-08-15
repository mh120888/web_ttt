(defproject web-ttt "0.1.0-SNAPSHOT"
  :description "A web-based tic tac toe game"
  :url "https://github.com/mh120888/web_ttt"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.match "0.3.0-alpha4"]
                 [hiccup "1.0.5"]
                 [matts-clojure-ttt "0.1.0-SNAPSHOT"]
                 [com.github.mh120888/javaserver "1.0-SNAPSHOT"]]
  :plugins [[speclj "3.3.0"]
            [org.clojars.punkisdead/lein-cucumber "1.0.7"]]
  :repositories {"sonatype snapshots" "https://oss.sonatype.org/content/repositories/snapshots"}
  :test-paths ["spec"]
  :cucumber-feature-paths ["features"]
  :main ^:skip-aot web-ttt.core
  :target-path "target/%s"
  :profiles { :dev {:dependencies [[speclj "3.3.0"]
                                   [info.cukes/cucumber-clojure "1.2.4"]]}
              :uberjar {:aot :all}})
