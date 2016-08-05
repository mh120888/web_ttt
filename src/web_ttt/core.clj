(ns web-ttt.core
  (:gen-class)
  (:require [web-ttt.app :as app]
    [matts-clojure-ttt.game :as game]
    [matts-clojure-ttt.console-ui :as ui]
    [clojure.core.match :as match])
  (:import [server.MyServer]
   [app.Application]
   [httpmessage.HTTPResponse]
   [cobspecapp.CobSpecApp]
   [basichttpmessage.BasicHTTPMessageFactory]))

(def message-factory (basichttpmessage.BasicHTTPMessageFactory.))

(def basic-app (reify app.Application
  (getResponse [this request response]
    (app/get-response request response))))

(defn -main
  [& args]
  (server.MyServer/runServer basic-app message-factory))
