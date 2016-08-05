(ns web-ttt.core
  (:gen-class)
  (:require [matts-clojure-ttt.game :as game]
    [matts-clojure-ttt.console-ui :as ui]
    [clojure.core.match :as match])
  (:import [server.MyServer]
   [app.Application]
   [httpmessage.HTTPResponse]
   [cobspecapp.CobSpecApp]
   [basichttpmessage.BasicHTTPMessageFactory]))

(def message-factory (basichttpmessage.BasicHTTPMessageFactory.))

(deftype WebIO []
  ui/IOProtocol
  (io-print-line [type message]
    (.getBytes message))
  (io-read [type]
    (read-line)))

(def basic-app (reify app.Application
  (getResponse [this request response]
    (let [response (.getNewResponse message-factory)
      method (.getMethod request)
      path (.getPath request)
      params (into {} (.getAllParams request))]
      (.setHTTPVersion response "HTTP/1.1")
      (println params)
      (match/match [method path params]
        ["GET" "/" _] (do
          (.setStatus response 200)
          (.addHeader response "Content-Type" "text/html; charset=utf-8")
          (.setBody response (.getBytes (slurp "resources/index.html"))))
        [_ "/" _] (.setStatus response 405)
        ["GET" "/new-game" {"size" _ "marker" _ "gofirst" _}] (do
          (.setStatus response 200)
          (.addHeader response "Content-Type" "text/html; charset=utf-8")
          (.setBody response (ui/io-print-line (WebIO.) "Let's play a game of tic tac toe")))
        ["GET", "/new-game" _] (.setStatus response 422)
        [_ _ _] (.setStatus response 404))
      response))))

(defn -main
  [& args]
  (server.MyServer/runServer basic-app message-factory))
