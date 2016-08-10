(ns web-ttt.new-game-action
  (:require [matts-clojure-ttt.console-ui :as ui]
    [web-ttt.board-as-html :as board-as-html]
    [matts-clojure-ttt.board :as board]
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
      [{"size" (:or "3" "4"), "marker" (:or "x" "o"), "gofirst" (:or "y" "n")}]
        (do
          (.setStatus response 200)
          (.addHeader response "Content-Type" "text/html; charset=utf-8")
          (.setBody response (ui/io-print-line (WebIO.) (str "Let's play a game of tic tac toe" (board-as-html/render-board (board/generate-new-board (Integer/parseInt (get params "size"))))))))
      [_] (.setStatus response 422))))
