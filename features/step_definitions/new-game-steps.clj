(use 'web-ttt.core)
(use 'speclj.core)

(def request (.getNewRequest message-factory))
(def new-response (.getNewResponse message-factory))
(def empty-board-3 (clojure.string/replace (slurp "resources/_empty_board_3.html") #"\s\s+" ""))

(When #"^I choose to play a new game with my preferences$" []
  (.setRequestLine request "GET /new-game?marker=x&gofirst=y&size=3 HTTP/1.1")
  (def app-response (.getFormattedResponse (.getResponse basic-app request new-response))))

(Then #"^the response should contain an empty board$" []
  (should-contain empty-board-3 app-response))