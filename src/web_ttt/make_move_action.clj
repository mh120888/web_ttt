(ns web-ttt.make-move-action
  (:require [web-ttt.board-as-html :as board-as-html]
    [clojure.core.match :as match]
    [matts-clojure-ttt.board :as board]))

(defn get-response
  [request response]
  (let [params (into {} (.getAllParams request))]
    (.setHTTPVersion response "HTTP/1.1")
    (match/match [params]
      [{"space" _, "marker" _, "board" _}]
      (let [next-board (board/mark-space (read-string (get params "board")) (Integer/parseInt (get params "space")) (get params "marker"))]
        (do
          (.setHTTPVersion response "HTTP/1.1")
          (.setStatus response 200)
          (.addHeader response "Content-Type" "text/html; charset=utf-8")
          (.setBody response (.getBytes (board-as-html/render-board next-board (get params "marker"))))))
      [_] (.setStatus response 422))))
