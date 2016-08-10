(require '[web-ttt.core :refer :all]
  '[speclj.core :refer :all]
  '[hiccup.core :as hiccup])

(def request (.getNewRequest message-factory))
(def new-response (.getNewResponse message-factory))
(def empty-board-3 (clojure.string/replace (slurp "resources/_empty_board_3.html") #"\s\s+" ""))

(defn marked-space-html
  [space marker]
  (hiccup/html [:span {:class "space" :data-space space}
    [:span marker]]))

(Given #"^I choose to play a new game with the following preferences$" [data]
  (let [preferences (into {} (table->rows data))
        marker (:marker preferences)
        gofirst (:gofirst preferences)
        size (:size preferences)]
    (.setRequestLine request (str "GET /new-game?marker=" marker "&gofirst=" gofirst "&size=" size " HTTP/1.1"))
    (def app-response (.getFormattedResponse (.getResponse basic-app request new-response)))))

(When #"^I choose to play a new game with my preferences$" []
  (.setRequestLine request "GET /new-game?marker=x&gofirst=y&size=3 HTTP/1.1")
  (def app-response (.getFormattedResponse (.getResponse basic-app request new-response))))

(Then #"^the response should contain an empty board$" []
  (should-contain empty-board-3 app-response))

(When #"^I play on space (\d+)$" [space]
  (.setRequestLine request (str "GET /make-move?space=" space " HTTP/1.1"))
  (def app-response (.getFormattedResponse (.getResponse basic-app request (.getNewResponse message-factory)))))

(Then #"^the response should contain a board with space (\d+) taken by x$" [space]
  (should-contain (marked-space-html space "x") app-response))