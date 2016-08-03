(ns web-ttt.core
  (:gen-class)
  (:require [matts-clojure-ttt.game :as game]))

(defn -main
  [& args]
  (game/-main))
