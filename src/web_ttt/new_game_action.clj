(ns web-ttt.new-game-action
  (:require [matts-clojure-ttt.console-ui :as ui]
    [clojure.core.match :as match]))

(deftype WebIO []
  ui/IOProtocol
  (io-print-line [type message]
    (.getBytes message))
  (io-read [type]
    (read-line)))


(defn get-response [request response]
  (let [params (into {} (.getAllParams request))]
    (.setHTTPVersion response "HTTP/1.1")
    (match/match [params]
      [{"size" "3", "marker" (:or "x" "o"), "gofirst" (:or "y" "n")}]
      (do
        (.setStatus response 200)
        (.addHeader response "Content-Type" "text/html; charset=utf-8")
        (.setBody response (ui/io-print-line (WebIO.) (str "Let's play a game of tic tac toe" (slurp "resources/_empty_board_3.html")))))
      [{"size" "4", "marker" (:or "x" "o"), "gofirst" (:or "y" "n")}]
      (do
        (.setStatus response 200)
        (.addHeader response "Content-Type" "text/html; charset=utf-8")
        (.setBody response (ui/io-print-line (WebIO.) (str "Let's play a game of tic tac toe" (slurp "resources/_empty_board_4.html")))))
    [_]
    (do
      (.setStatus response 422)))))
