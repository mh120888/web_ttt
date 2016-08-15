(ns web-ttt.router
  (:require [clojure.core.match :as match]
    [web-ttt.action :as action])
  (:import [web_ttt.action HomepageAction]
   [web_ttt.action MethodNotAllowedAction]
   [web_ttt.action NewGameAction]
   [web_ttt.action MakeMoveAction]
   [web_ttt.action ComputerMoveAction]
   [web_ttt.action NotFoundAction]))

(defn route-and-return-action [method path]
  (match/match [method path]
    ["GET" "/"] (action/HomepageAction.)
    [_ "/"] (action/MethodNotAllowedAction.)
    ["GET", "/new-game"] (action/NewGameAction.)
    ["GET", "/make-move"] (action/MakeMoveAction.)
    ["GET", "/computer-move"] (action/ComputerMoveAction.)
    [_ _] (action/NotFoundAction.)))