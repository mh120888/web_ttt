(ns web-ttt.core
  (:gen-class)
  (:require [matts-clojure-ttt.game :as game])
  (:import [server.MyServer]
           [app.Application]
           [httpmessage.HTTPResponse]
           [cobspecapp.CobSpecApp]
           [basichttpmessage.BasicHTTPMessageFactory]))

(def message-factory (basichttpmessage.BasicHTTPMessageFactory.))

(def basic-app (reify app.Application
  (getResponse [this request response]
    (let [response (.getNewResponse message-factory)]
      (if (= "/" (.getPath request))
        (.setStatus response 200)
        (.setStatus response 404))
      response))))

(defn -main
  [& args]
  (server.MyServer/runServer basic-app message-factory))
