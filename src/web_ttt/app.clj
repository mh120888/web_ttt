(ns web-ttt.app
  (:require [web-ttt.action :as action]
    [web-ttt.router :as router])
  (:import [basichttpmessage.BasicHTTPMessageFactory]))

(def message-factory (basichttpmessage.BasicHTTPMessageFactory.))

(defn get-response [request response]
  (let [method (.getMethod request)
        path (.getPath request)
        params (into {} (.getAllParams request))
        action (router/route-and-return-action method path params)]
    (.setHTTPVersion response "HTTP/1.1")
    (action/get-response action request response)
    response))