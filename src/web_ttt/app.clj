(ns web-ttt.app
  (:require [web-ttt.action :as action]
    [web-ttt.router :as router]))

(defn get-response [request response]
  (let [method (.getMethod request)
        path (.getPath request)
        action (router/route-and-return-action method path)]
    (.setHTTPVersion response "HTTP/1.1")
    (action/get-response action request response)
    response))