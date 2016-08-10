(ns web-ttt.action
  (:require [web-ttt.new-game-action :as new-game-action]
    [matts-clojure-ttt.board :as board]
    [web-ttt.board-as-html :as board-as-html]
    [clojure.core.match :as match]))

(defprotocol Action
  (get-response [type request response]))

(deftype StaticResourceAction []
  Action
  (get-response [type request response]
    (do
      (.setStatus response 200)
      (.setHTTPVersion response "HTTP/1.1")
      (.addHeader response "Content-Type" "text/html; charset=utf-8")
      (.setBody response (.getBytes (slurp "resources/index.html"))))))

(deftype MethodNotAllowedAction []
  Action
  (get-response [type request response] (.setStatus response 405)))

(deftype UnprocessableEntityAction []
  Action
  (get-response [type request response] (.setStatus response 422)))

(deftype NotFoundAction []
  Action
  (get-response [type request response] (.setStatus response 404)))

(deftype NewGameAction []
  Action
  (get-response [type request response]
    (new-game-action/get-response request response)))

(deftype MakeMoveAction []
  Action
  (get-response [type request response]
    (let [params (into {} (.getAllParams request))
          next-board (board/mark-space (read-string (get params "board")) (Integer/parseInt (get params "space")) (get params "marker"))]
      (.setHTTPVersion response "HTTP/1.1")
      (.setStatus response 200)
      (.addHeader response "Content-Type" "text/html; charset=utf-8")
      (.setBody response (.getBytes (board-as-html/render-board next-board (get params "marker")))))))
