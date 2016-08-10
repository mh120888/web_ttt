(ns web-ttt.core-spec
  (:require [speclj.core :refer :all]
    [web-ttt.core :refer :all]))

(describe "basic-app"
  (before-all
    (def request (.getNewRequest message-factory))
    (def new-response (.getNewResponse message-factory)))

  (context "GET /"
    (before-all
      (.setRequestLine request "GET / HTTP/1.1")
      (def app-response (.getResponse basic-app request new-response)))

    (it "returns a 200"
      (should-contain "200" (.getFormattedResponse app-response)))

    (it "returns a content-type of text/html"
     (should-contain "Content-Type: text/html" (.getFormattedResponse app-response)))

    (it "returns the contents of the index.html"
      (should-contain "Do you want to play a game?" (.getFormattedResponse app-response))))

  (context "POST /"
    (it "returns a 405"
      (.setRequestLine request "POST / HTTP/1.1")

      (def app-response (.getResponse basic-app request new-response))

      (should-contain "405" (.getFormattedResponse app-response))))

  (context "GET /new-game (w/params)"
    (before-all
      (.setRequestLine request "GET /new-game?marker=x&gofirst=y&size=3 HTTP/1.1")
      (def app-response (.getResponse basic-app request new-response)))

    (it "returns a 200 if params are present"
      (should-contain "200" (.getFormattedResponse app-response)))

    (it "should return the welcome text"
      (should-contain "Let's play a game of tic tac toe" (.getFormattedResponse app-response))))

  (context "GET /new-game (w/o params)"
    (it "returns a 422"
      (def new-request (.getNewRequest message-factory))

      (.setRequestLine new-request "GET /new-game HTTP/1.1")
      (def app-response (.getResponse basic-app new-request new-response))

      (should-contain "422" (.getFormattedResponse app-response))))

  (context "GET /(any-other-path)"
    (it "returns a 404"
      (.setRequestLine request "GET /fakepath HTTP/1.1")

      (def app-response (.getResponse basic-app request new-response))

      (should-contain "404" (.getFormattedResponse app-response)))))

(run-specs)