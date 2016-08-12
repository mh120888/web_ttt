(ns web-ttt.new-game-action
  (:require [matts-clojure-ttt.console-ui :as ui]
    [web-ttt.board-state :as board-state]
    [web-ttt.board-as-html :as board-as-html]
    [matts-clojure-ttt.board :as board]
    [clojure.core.match :as match]))

(defn set-current-turn-and-human-marker
  [gofirst marker]
  (board-state/set-human-marker marker)
  (if (= "y" gofirst)
    (board-state/set-turn marker)
    (board-state/set-turn (board/get-other-marker marker))))

(defn get-response [request response]
  (let [params (into {} (.getAllParams request))]
    (.setHTTPVersion response "HTTP/1.1")
    (match/match [params]
      [{"size" (:or "3" "4"), "marker" (:or "x" "o"), "gofirst" (:or "y" "n")}]
      (do
        (board-state/update-board (board/generate-new-board (Integer/parseInt (get params "size"))))
        (set-current-turn-and-human-marker (get params "gofirst") (get params "marker"))
        (.setStatus response 200)
        (.addHeader response "Content-Type" "text/html; charset=utf-8")
        (.setBody response (.getBytes (str (board-as-html/generate-html-response (board-state/get-board) (get params "marker")) "<p>Let's play a game of tic tac toe</p>"))))
      [_] (.setStatus response 422))))
