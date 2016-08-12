(ns web-ttt.make-move-action
  (:require [web-ttt.board-as-html :as board-as-html]
    [web-ttt.board-state :as board-state]
    [clojure.core.match :as match]
    [matts-clojure-ttt.board :as board]))

(defn get-board
  [params]
  (board-state/get-board))

(defn get-space
  [params]
  (Integer/parseInt (get params "space")))

(defn get-marker
  [params]
  (get params "marker"))

(defn get-response
  [request response]
  (let [params (into {} (.getAllParams request))]
    (.setHTTPVersion response "HTTP/1.1")
    (match/match [params]
      [{"space" _, "marker" _}]
      (let [next-board (board/mark-space (get-board params) (get-space params) (get-marker params))
            game-over (board/game-over? next-board)
            extra-message (if game-over
                            "Game Over"
                            "")]
        (do
          (board-state/update-board next-board)
          (.setHTTPVersion response "HTTP/1.1")
          (.setStatus response 200)
          (.addHeader response "Content-Type" "text/html; charset=utf-8")
          (.setBody response (.getBytes (board-as-html/generate-html-response next-board (get params "marker"))))))
      [_] (.setStatus response 422))))
