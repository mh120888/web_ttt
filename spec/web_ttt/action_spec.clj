(ns web-ttt.action-spec
  (:require [speclj.core :refer :all]
    [web-ttt.action :refer :all]
    [hiccup.core :as hiccup]
    [web-ttt.core :as core])
  (:import [web_ttt.action HomepageAction]
   [web_ttt.action MethodNotAllowedAction]
   [web_ttt.action NewGameAction]
   [web_ttt.action MakeMoveAction]
   [web_ttt.action NotFoundAction]))

(defn marked-space-html
  [space marker]
  (hiccup/html [:span {:class "space" :data-space space}
    [:span {:class "marker"} marker]]))

(describe "MethodNotAllowedAction#get-response"
  (before-all
    (def request (.getNewRequest core/message-factory))
    (def response (.getNewResponse core/message-factory)))

  (it "returns a response with a status of 405"
    (get-response (MethodNotAllowedAction.) request response)
    (should-contain "405" (.getFormattedResponse response))))

(describe "NotFoundAction#get-response"
  (before-all
    (def request (.getNewRequest core/message-factory))
    (def response (.getNewResponse core/message-factory)))

  (it "returns a response with a status of 404"
    (get-response (NotFoundAction.) request response)
    (should-contain "404" (.getFormattedResponse response))))

(describe "HomepageAction#get-response"
  (before-all
    (def request (.getNewRequest core/message-factory))
    (def response (.getNewResponse core/message-factory))
    (get-response (HomepageAction.) request response))

  (it "returns a response with a status of 200"
    (should-contain "200" (.getFormattedResponse response)))

  (it "returns a response with a content-type of text/html; charset=utf-8"
    (should-contain "Content-Type: text/html; charset=utf-8" (.getFormattedResponse response))))