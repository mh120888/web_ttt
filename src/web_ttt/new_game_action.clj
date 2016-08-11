(ns web-ttt.new-game-action
  (:require [matts-clojure-ttt.console-ui :as ui]
    [web-ttt.board-as-html :as board-as-html]
    [matts-clojure-ttt.board :as board]
    [clojure.core.match :as match]))

(defn get-response [request response]
  (let [params (into {} (.getAllParams request))]
    (.setHTTPVersion response "HTTP/1.1")
    (match/match [params]
      [{"size" (:or "3" "4"), "marker" (:or "x" "o"), "gofirst" (:or "y" "n")}]
        (do
          (.setStatus response 200)
          (.addHeader response "Content-Type" "text/html; charset=utf-8")
          (.setBody response (.getBytes (str (board-as-html/generate-html-response (board/generate-new-board (Integer/parseInt (get params "size"))) (get params "marker")) "<p>Let's play a game of tic tac toe</p>"))))
      [_] (.setStatus response 422))))
