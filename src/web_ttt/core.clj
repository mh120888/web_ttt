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
      (.setHTTPVersion response "HTTP/1.1")
      (if (= "/" (.getPath request))
        (do (.setStatus response 200)
            (.addHeader response "Content-Type" "text/html; charset=utf-8")
            (.setBody response (.getBytes (slurp "resources/index.html"))))
        (.setStatus response 404))
      response))))

(defn -main
  [& args]
  (server.MyServer/runServer basic-app message-factory))
