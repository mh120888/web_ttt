(use 'web-ttt.core)
(use 'speclj.core)

(def request (.getNewRequest message-factory))
(def new-response (.getNewResponse message-factory))

(Given #"^I am a user$" [])

(When #"^I go to the homepage$" []
  (.setRequestLine request "GET / HTTP/1.1")
  (def app-response (.getFormattedResponse (.getResponse basic-app request new-response))))

(Then #"^the response should be a (\d+)$" [status]
  (should-contain status app-response))

(Then #"^the response should contain the text \"([^\"]*)\"$" [text]
  (should-contain text app-response))