(ns web-ttt.core
  (:gen-class)
  (:require [web-ttt.app :as app])
  (:import [server.MyServer]
   [app.Application]
   [basichttpmessage.BasicHTTPMessageFactory]))

(def message-factory (basichttpmessage.BasicHTTPMessageFactory.))

(def basic-app (reify app.Application
  (getResponse [this request response]
    (app/get-response request response))))

(defn -main
  [& args]
  (server.MyServer/runServer basic-app message-factory))
