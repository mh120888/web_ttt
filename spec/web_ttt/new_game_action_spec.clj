(ns web-ttt.new-game-action-spec
  (:require [speclj.core :refer :all]
    [web-ttt.core :as core]
    [web-ttt.new-game-action :refer :all]))

(describe "#get-response"
  (before-all
    (def request (.getNewRequest core/message-factory))
    (def response (.getNewResponse core/message-factory)))

  (it "returns a response with a status of 200 w/correct parameters"
    (.setRequestLine request "GET /new-game?marker=x&gofirst=y&size=3 HTTP/1.1")
    (get-response request response)
    (should-contain "200" (.getFormattedResponse response)))

  (it "returns a response with a status of 422 w/incorrect parameters"
    (.setRequestLine request "GET /new-game?marker=incorrect&gofirst=incorrect&size=randomwords HTTP/1.1")
    (get-response request response)
    (should-contain "422" (.getFormattedResponse response)))

  (it "returns a response with a status of 422 w/missing parameters"
    (.setRequestLine request "GET /new-game HTTP/1.1")
    (get-response request response)
    (should-contain "422" (.getFormattedResponse response))))