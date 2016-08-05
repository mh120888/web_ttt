(ns web-ttt.core-spec
  (:require [speclj.core :refer :all]
    [web-ttt.core :refer :all]))

(describe "basic-app"
  (before-all
    (def request (.getNewRequest message-factory))
    (def new-response (.getNewResponse message-factory)))

  (context "GET /"
    (it "returns a 200"
      (.setRequestLine request "GET / HTTP/1.1")

      (def app-response (.getResponse basic-app request new-response))

      (should-contain "200" (.getFormattedResponse app-response)))

    (it "returns a content-type of text/html"
      (.setRequestLine request "GET / HTTP/1.1")

      (def app-response (.getResponse basic-app request new-response))

      (should-contain "Content-Type: text/html" (.getFormattedResponse app-response)))

    (it "returns the contents of the index.html"
      (.setRequestLine request "GET / HTTP/1.1")

      (def app-response (.getResponse basic-app request new-response))

      (should-contain "Do you want to play a game?" (.getFormattedResponse app-response))))

  (context "GET /(any-other-path)"
    (it "returns a 404"
      (.setRequestLine request "GET /fakepath HTTP/1.1")

      (def app-response (.getResponse basic-app request new-response))

      (should-contain "404" (.getFormattedResponse app-response)))))

(run-specs)