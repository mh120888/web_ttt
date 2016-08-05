(ns web-ttt.action
  (:require [matts-clojure-ttt.console-ui :as ui]))

(defprotocol Action
  (get-response [type request response]))

(deftype StaticResourceAction []
  Action
  (get-response [type request response]
    (do
      (.setStatus response 200)
      (.addHeader response "Content-Type" "text/html; charset=utf-8")
      (.setBody response (.getBytes (slurp "resources/index.html"))))))

(deftype MethodNotAllowedAction []
  Action
  (get-response [type request response] (.setStatus response 405)))

(deftype UnprocessableEntityAction []
  Action
  (get-response [type request response] (.setStatus response 422)))

(deftype NotFoundAction []
  Action
  (get-response [type request response] (.setStatus response 404)))

(deftype WebIO []
  ui/IOProtocol
  (io-print-line [type message]
    (.getBytes message))
  (io-read [type]
    (read-line)))

(deftype NewGameAction []
  Action
  (get-response [type request response] (do
        (.setStatus response 200)
        (.addHeader response "Content-Type" "text/html; charset=utf-8")
        (.setBody response (ui/io-print-line (WebIO.) "Let's play a game of tic tac toe")))))


