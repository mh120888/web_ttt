(ns web-ttt.core
  (:gen-class)
  (:require [matts-clojure-ttt.game :as game]
            [clojure.core.match :as match])
  (:import [server.MyServer]
           [app.Application]
           [httpmessage.HTTPResponse]
           [cobspecapp.CobSpecApp]
           [basichttpmessage.BasicHTTPMessageFactory]))

(def message-factory (basichttpmessage.BasicHTTPMessageFactory.))

(def basic-app (reify app.Application
  (getResponse [this request response]
    (let [response (.getNewResponse message-factory)
          method (.getMethod request)
          path (.getPath request)]
      (.setHTTPVersion response "HTTP/1.1")
      (match/match [method path]
        ["GET" "/"] (do
                      (.setStatus response 200)
                      (.addHeader response "Content-Type" "text/html; charset=utf-8")
                      (.setBody response (.getBytes (slurp "resources/index.html"))))
        [_ "/"] (.setStatus response 405)
        ["GET" "/new-game"] (do
                      (.setStatus response 200)
                      (.addHeader response "Content-Type" "text/html; charset=utf-8"))
        [_ _] (.setStatus response 404))
      response))))

(defn -main
  [& args]
  (server.MyServer/runServer basic-app message-factory))
