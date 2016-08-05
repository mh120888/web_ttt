(ns web-ttt.router
  (:require [clojure.core.match :as match]
    [web-ttt.action :as action])
  (:import [web_ttt.action StaticResourceAction]
   [web_ttt.action MethodNotAllowedAction]
   [web_ttt.action NewGameAction]
   [web_ttt.action UnprocessableEntityAction]
   [web_ttt.action NotFoundAction]))

(defn route-and-return-action [method path params]
  (match/match [method path params]
    ["GET" "/" _] (action/StaticResourceAction.)
    [_ "/" _] (action/MethodNotAllowedAction.)
    ["GET" "/new-game" {"size" _ "marker" _ "gofirst" _}] (action/NewGameAction.)
    ["GET", "/new-game" _] (action/UnprocessableEntityAction.)
    [_ _ _] (action/NotFoundAction.)))