(ns web-ttt.computer-move-action-spec
  (:require [speclj.core :refer :all]
    [web-ttt.computer-move-action :refer :all]
    [web-ttt.core :as core]
    [web-ttt.action :as action]
    [web-ttt.board-state :as board-state]
    [matts-clojure-ttt.board :as board]
    [web-ttt.router :refer :all]))


(describe "computer-move-action#get-reponse"
    (context "with an empty 3x3 board on which the computer will play first with marker o"
      (before-all
        (board-state/update-board (board/generate-new-board 3))
        (board-state/set-human-marker "x")
        (board-state/set-turn "o")
        (def request (.getNewRequest core/message-factory))
        (def response (.getNewResponse core/message-factory))
        (.setRequestLine request "GET /computer-move HTTP/1.1")
        (get-response request response))

      (it "returns a response with a status of 200"
        (should-contain "200" (.getFormattedResponse response)))

      (it "returns a response that does not contain a link to get next computer move"
        (should-not-contain "/computer-move" (.getFormattedResponse response)))

      (it "switches the current player to the human"
        (should= (board-state/get-human-marker) (board-state/get-current-player)))

      (it "updates the board state"
        (should-not= (board/generate-new-board 3) (board-state/get-board))))

    (context "with an empty 3x3 board with a human player going first"
      (before-all
        (board-state/update-board (board/generate-new-board 3))
        (board-state/set-human-marker "x")
        (board-state/set-turn "x")
        (def request (.getNewRequest core/message-factory))
        (def response (.getNewResponse core/message-factory))
        (.setRequestLine request "GET /computer-move HTTP/1.1")
        (get-response request response))

      (it "returns a response with a status of 403 if the computer player tries playing first"
        (should-contain "403" (.getFormattedResponse response)))))