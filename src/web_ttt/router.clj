(ns web-ttt.router
  (:require [clojure.core.match :as match]
    [web-ttt.action :as action])
  (:import [web_ttt.action StaticResourceAction]
   [web_ttt.action MethodNotAllowedAction]
   [web_ttt.action NewGameAction]
   [web_ttt.action MakeMoveAction]
   [web_ttt.action UnprocessableEntityAction]
   [web_ttt.action NotFoundAction]))

(defn route-and-return-action [method path]
  (match/match [method path]
    ["GET" "/"] (action/StaticResourceAction.)
    [_ "/"] (action/MethodNotAllowedAction.)
    ["GET", "/new-game"] (action/NewGameAction.)
    ["GET", "/make-move"] (action/MakeMoveAction.)
    [_ _] (action/NotFoundAction.)))