(ns web-ttt.app
  (:require [web-ttt.action :as action]
    [matts-clojure-ttt.game :as game]
    [clojure.core.match :as match])
  (:import [web_ttt.action StaticResourceAction]
   [web_ttt.action MethodNotAllowedAction]
   [web_ttt.action NewGameAction]
   [web_ttt.action UnprocessableEntityAction]
   [web_ttt.action NotFoundAction]
   [server.MyServer]
   [app.Application]
   [httpmessage.HTTPResponse]
   [cobspecapp.CobSpecApp]
   [basichttpmessage.BasicHTTPMessageFactory]))

(def message-factory (basichttpmessage.BasicHTTPMessageFactory.))

(defn get-response [request response]
  (let [response (.getNewResponse message-factory)
    method (.getMethod request)
    path (.getPath request)
    params (into {} (.getAllParams request))]
    (.setHTTPVersion response "HTTP/1.1")
    (match/match [method path params]
      ["GET" "/" _]
        (action/get-response (action/StaticResourceAction.) request response)
      [_ "/" _]
        (action/get-response (action/MethodNotAllowedAction.) request response)
      ["GET" "/new-game" {"size" _ "marker" _ "gofirst" _}]
        (action/get-response (action/NewGameAction.) request response)
      ["GET", "/new-game" _]
        (action/get-response (action/UnprocessableEntityAction.) request response)
      [_ _ _]
        (action/get-response (action/NotFoundAction.) request response))
    response))