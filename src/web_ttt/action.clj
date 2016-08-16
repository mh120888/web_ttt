(ns web-ttt.action
  (:require [web-ttt.new-game-action :as new-game-action]
    [web-ttt.response-helper :refer :all]
    [web-ttt.make-move-action :as make-move-action]
    [web-ttt.computer-move-action :as computer-move-action]
    [matts-clojure-ttt.board :as board]
    [web-ttt.board-as-html :as board-as-html]
    [clojure.core.match :as match]))

(defprotocol Action
  (get-response [type request response]))

(deftype HomepageAction []
  Action
  (get-response [type request response]
    (do
      (set-response-to 200 response)
      (.addHeader response "Content-Type" "text/html; charset=utf-8")
      (.setBody response (.getBytes (slurp "resources/index.html"))))))

(deftype MethodNotAllowedAction []
  Action
  (get-response [type request response] (set-response-to 405 response)))

(deftype NotFoundAction []
  Action
  (get-response [type request response] (set-response-to 404 response)))

(deftype NewGameAction []
  Action
  (get-response [type request response]
    (new-game-action/get-response request response)))

(deftype MakeMoveAction []
  Action
  (get-response [type request response]
    (make-move-action/get-response request response)))

(deftype ComputerMoveAction []
  Action
  (get-response [type request response]
    (computer-move-action/get-response request response)))