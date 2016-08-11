(require '[web-ttt.core :refer :all]
  '[matts-clojure-ttt.board :as board]
  '[speclj.core :refer :all]
  '[hiccup.core :as hiccup])

(def request (.getNewRequest message-factory))
(def new-response (.getNewResponse message-factory))
(def empty-board-3 (clojure.string/replace (slurp "resources/_empty_board_3.html") #"\s\s+" ""))
(def empty-board-4 (clojure.string/replace (slurp "resources/_empty_board_4.html") #"\s\s+" ""))

(Given #"^I am a user$" [])

(When #"^I go to the homepage$" []
  (.setRequestLine request "GET / HTTP/1.1")
  (def app-response (.getFormattedResponse (.getResponse basic-app request new-response))))

(Then #"^the response should be a (\d+)$" [status]
  (should-contain status app-response))


(defn marked-space-html
  [space marker]
  (hiccup/html [:span {:class "space" :data-space space}
    [:span {:class "marker"} marker]]))

(defn generate-board-state
  [board space moves-left-to-play]
  (if (empty? moves-left-to-play)
    board
    (let [marker (first moves-left-to-play)
      space space
      next-board (if (not= "_" marker)
                  (board/mark-space board space marker)
                  board)
      rest-of-moves (rest moves-left-to-play)]
      (recur next-board (inc space) rest-of-moves))))

(Given #"^I choose to play a new game with the following preferences$" [data]
  (let [preferences (into {} (table->rows data))
    marker (:marker preferences)
    gofirst (:gofirst preferences)
    size (:size preferences)]
    (def marker (:marker preferences))
    (def new-board (board/generate-new-board size))
    (.setRequestLine request (str "GET /new-game?marker=" marker "&gofirst=" gofirst "&size=" size " HTTP/1.1"))
    (def app-response (.getFormattedResponse (.getResponse basic-app request new-response)))))

(Given #"^the board is in the following state$" [board-state]
  (def new-board (generate-board-state new-board 0 (clojure.string/split board-state #"\s+"))))

(When #"^I choose to play a new game with my preferences$" []
  (.setRequestLine request "GET /new-game?marker=x&gofirst=y&size=3 HTTP/1.1")
  (def app-response (.getFormattedResponse (.getResponse basic-app request new-response))))

(Then #"^the response should contain an empty board of size (\d+)$" [size]
  (if (= "3" size)
    (should-contain empty-board-3 app-response)
    (should-contain empty-board-4 app-response)))

(When #"^I play on space (\d+)$" [space]
  (let [request-line (str "GET /make-move?space=" space "&marker=" marker "&board=" (clojure.string/replace new-board #"\s" "") " HTTP/1.1")]
    (.setRequestLine request request-line)
    (def app-response (.getFormattedResponse (.getResponse basic-app request (.getNewResponse message-factory))))))

(Then #"^the response should contain a board with space (\d+) taken by (\w+)$" [space marker]
  (should-contain (marked-space-html space marker) app-response))

(Then #"^space (\d+) is not available to be played on again$" [space]
  (should-contain (marked-space-html space marker) app-response)
  (should-not-contain (str "/make-move?space=" space) app-response))

(Then #"^the response should contain the text \"([^\"]*)\"$" [text]
  (should-contain text app-response))