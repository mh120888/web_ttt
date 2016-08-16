(ns web-ttt.make-move-action
  (:require [web-ttt.board-as-html :as board-as-html]
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

(defn- return-response-with
  [status response]
  (.setHTTPVersion response "HTTP/1.1")
  (.setStatus response status))

(defn- make-move
  [params request response]
  (let [board (get-board params)
        space (get-space params)]
    (if (board/valid-move? board (str space))
      (let [next-board (board/mark-space board space (get-marker params))]
        (do
          (board-state/update-board next-board)
          (board-state/change-turn)
          (return-response-with 200 response)
          (.addHeader response "Content-Type" "text/html; charset=utf-8")
          (.setBody response (.getBytes (board-as-html/generate-html-response next-board (board-state/get-human-marker) :computers-turn)))))
      (return-response-with 403 response))))

(defn get-response
  [request response]
  (let [params (into {} (.getAllParams request))
    current-player (board-state/get-current-player)]
    (match/match [params]
      [{"space" _, "marker" current-player}] (make-move params request response)
      [{"space" _, "marker" _}] (return-response-with 403 response)
      [_] (return-response-with 422 response))))
