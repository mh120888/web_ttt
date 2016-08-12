(ns web-ttt.new-game-action-spec
  (:require [speclj.core :refer :all]
    [matts-clojure-ttt.board :as board]
    [web-ttt.board-state :as board-state]
    [web-ttt.core :as core]
    [web-ttt.new-game-action :refer :all]))

(describe "NewGameAction#get-response"
  (before-all
    (def request (.getNewRequest core/message-factory))
    (def response (.getNewResponse core/message-factory)))

  (context "with correct parameters"
    (before-all
      (board-state/update-board {})
      (board-state/set-turn "")
      (.setRequestLine request "GET /new-game?marker=o&gofirst=y&size=3 HTTP/1.1")
      (get-response request response)
      (def app-response (.getFormattedResponse response)))

    (it "returns a response with a status of 200"
      (should-contain "200" app-response))

    (it "sets the board state to a board of the appropriate size"
      (should= (board/generate-new-board 3) (board-state/get-board)))

    (it "sets the current player to the human player if human chose to go first"
      (should= "o" (board-state/get-current-player)))

    (it "sets the human marker to the chosen marker"
      (should= "o" (board-state/get-human-marker)))

    (it "sets the current player to the opponent if human chose to go second"
      (.setRequestLine request "GET /new-game?marker=o&gofirst=n&size=3 HTTP/1.1")
      (get-response request response)
      (def app-response (.getFormattedResponse response))
      (should= "x" (board-state/get-current-player)))

    (it "contains an empty board in the response"
      (should-contain (clojure.string/replace (slurp "resources/_empty_board_3.html") #"\s\s+" "") app-response)))

  (it "returns a response with a 4x4 empty board when the 4x4 board size option is chosen"
    (.setRequestLine request "GET /new-game?marker=o&gofirst=y&size=4 HTTP/1.1")
    (get-response request response)
    (should-contain (clojure.string/replace (slurp "resources/_empty_board_4.html") #"\s\s+" "") (.getFormattedResponse response)))

  (it "returns a response with a status of 422 w/incorrect parameters"
    (.setRequestLine request "GET /new-game?marker=incorrect&gofirst=incorrect&size=randomwords HTTP/1.1")
    (get-response request response)
    (should-contain "422" (.getFormattedResponse response)))

  (it "returns a response with a status of 422 w/missing parameters"
    (.setRequestLine request "GET /new-game HTTP/1.1")
    (get-response request response)
    (should-contain "422" (.getFormattedResponse response))))