(ns web-ttt.action-spec
  (:require [speclj.core :refer :all]
    [web-ttt.action :refer :all]
    [web-ttt.core :as core])
  (:import [web_ttt.action StaticResourceAction]
   [web_ttt.action MethodNotAllowedAction]
   [web_ttt.action NewGameAction]
   [web_ttt.action UnprocessableEntityAction]
   [web_ttt.action NotFoundAction]))

(describe "MethodNotAllowedAction#get-response"
  (before-all
    (def request (.getNewRequest core/message-factory))
    (def response (.getNewResponse core/message-factory)))

  (it "returns a response with a status of 405"
    (get-response (MethodNotAllowedAction.) request response)
    (should-contain "405" (.getFormattedResponse response))))

(describe "UnprocessableEntityAction#get-response"
  (before-all
    (def request (.getNewRequest core/message-factory))
    (def response (.getNewResponse core/message-factory)))

  (it "returns a response with a status of 422"
    (get-response (UnprocessableEntityAction.) request response)
    (should-contain "422" (.getFormattedResponse response))))

(describe "NotFoundAction#get-response"
  (before-all
    (def request (.getNewRequest core/message-factory))
    (def response (.getNewResponse core/message-factory)))

  (it "returns a response with a status of 404"
    (get-response (NotFoundAction.) request response)
    (should-contain "404" (.getFormattedResponse response))))

(describe "StaticResourceAction#get-response"
  (before-all
    (def request (.getNewRequest core/message-factory))
    (def response (.getNewResponse core/message-factory))
    (get-response (StaticResourceAction.) request response))

  (it "returns a response with a status of 200"
    (should-contain "200" (.getFormattedResponse response)))

  (it "returns a response with a content-type of text/html; charset=utf-8"
    (should-contain "Content-Type: text/html; charset=utf-8" (.getFormattedResponse response))))