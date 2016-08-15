(ns web-ttt.computer-move-action
  (:require [web-ttt.board-state :as board-state]
    [matts-clojure-ttt.board :as board]
    [web-ttt.board-as-html :as board-as-html]
    [matts-clojure-ttt.player :as player])
  (:import [matts_clojure_ttt.player ComputerPlayer]))

(def computer-player (ComputerPlayer.))

(defn make-move
  []
  (let [current-board (board-state/get-board)
        computer-marker (board/get-other-marker (board-state/get-human-marker))
        next-move (player/get-move computer-player current-board computer-marker)]
    (board-state/update-board (board/mark-space current-board next-move computer-marker))
    (board-state/change-turn)))

(defn get-response
  [request response]
  (.setHTTPVersion response "HTTP/1.1")
  (if (= (board-state/get-human-marker) (board-state/get-current-player))
    (.setStatus response 403)
    (do
      (make-move)
      (.setStatus response 200)
      (.addHeader response "Content-Type" "text/html; charset=utf-8")
      (.setBody response (.getBytes (board-as-html/generate-html-response (board-state/get-board) (board-state/get-human-marker) false))))))
