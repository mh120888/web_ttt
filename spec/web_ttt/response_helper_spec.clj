(ns web-ttt.response-helper-spec
  (:require [speclj.core :refer :all]
            [web-ttt.response-helper :refer :all]
            [web-ttt.core :as core]))

(describe "#set-response-to"
  (before-all
    (def response (.getNewResponse core/message-factory))
    (set-response-to 200 response)
    (def formatted-response (.getFormattedResponse response)))

  (it "changes the status of a response to the given status code"
    (should-contain "200" formatted-response))

  (it "sets the http version to HTTP/1.1"
    (should-contain "HTTP/1.1" formatted-response)))