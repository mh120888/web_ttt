(ns web-ttt.make-move-action
  (:require [web-ttt.board-as-html :as board-as-html]
    [web-ttt.response-helper :refer :all]
    [web-ttt.board-state :as board-state]
    [clojure.core.match :as match]
    [matts-clojure-ttt.board :as board]))

(defn- get-board
  [params]
  (board-state/get-board))

(defn- get-space
  [params]
  (Integer/parseInt (get params "space")))

(defn- get-marker
  [params]
  (get params "marker"))

(defn- make-move
  [params request response]
  (let [board (get-board params)
        space (get-space params)]
    (if (board/valid-move? board (str space))
      (let [next-board (board/mark-space board space (get-marker params))]
        (do
          (board-state/update-board next-board)
          (board-state/change-turn)
          (set-response-to 200 response)
          (.addHeader response "Content-Type" "text/html; charset=utf-8")
          (.setBody response (.getBytes (board-as-html/generate-html-response next-board (board-state/get-human-marker) :computers-turn)))))
      (set-response-to 403 response))))

(defn get-response
  [request response]
  (let [params (into {} (.getAllParams request))
    current-player (board-state/get-current-player)]
    (match/match [params]
      [{"space" _, "marker" current-player}] (make-move params request response)
      [{"space" _, "marker" _}] (set-response-to 403 response)
      [_] (set-response-to 422 response))))
