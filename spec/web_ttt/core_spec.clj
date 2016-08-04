(ns web-ttt.core-spec
  (:require [speclj.core :refer :all]
            [web-ttt.core :refer :all]))

(describe "basic-app"
  (before-all
    (def request (.getNewRequest message-factory))
    (def new-response (.getNewResponse message-factory)))

  (it "returns a 200 for GET / requests"
    (.setRequestLine request "GET / HTTP/1.1")

    (def app-response (.getResponse basic-app request new-response))

    (should-contain "200" (.getFormattedResponse app-response)))

  (it "returns a 404 for GET requests to paths that don't exist"
    (.setRequestLine request "GET /fakepath HTTP/1.1")

    (def app-response (.getResponse basic-app request new-response))

    (should-contain "404" (.getFormattedResponse app-response))))

(run-specs)