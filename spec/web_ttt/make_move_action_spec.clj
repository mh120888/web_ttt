(ns web-ttt.make-move-action-spec
  (:require [speclj.core :refer :all]
    [web-ttt.make-move-action :refer :all]
    [web-ttt.core :as core]
    [web-ttt.board-state :as board-state]
    [web-ttt.action-spec :as action-spec]
    [matts-clojure-ttt.board :as board]
    [clojure.core.match :as match]))

(describe "MakeMoveAction#get-response"

  (context "with correct parameters (space, marker) when it is this player's turn"
    (before-all
      (board-state/update-board (board/generate-new-board 3))
      (board-state/set-turn "o")
      (def request (.getNewRequest core/message-factory))
      (def response (.getNewResponse core/message-factory))
      (.setRequestLine request "GET /make-move?space=8&marker=o HTTP/1.1")
      (get-response request response))

    (it "returns a response with a status of 200"
      (should-contain "200" (.getFormattedResponse response)))

    (it "returns a response that shows the selected space was marked with the specified marker"
      (should-contain (action-spec/marked-space-html "8" "o") (.getFormattedResponse response)))

    (it "switches the current player"
      (should= "x" (board-state/get-current-player))))

  (context "with correct parameters (space, marker) but when it is the opponent's turn"
    (before-all
      (board-state/update-board (board/generate-new-board 3))
      (board-state/set-turn "x")
      (def request (.getNewRequest core/message-factory))
      (def response (.getNewResponse core/message-factory))
      (.setRequestLine request "GET /make-move?space=0&marker=o HTTP/1.1")
      (get-response request response))

    (it "returns a 403"
      (should-contain "403" (.getFormattedResponse response)))

    (it "does not switch the current player"
      (should= "x" (board-state/get-current-player))))

  (it "returns a 422 if a required parameter is missing"
    (board-state/update-board (board/generate-new-board 3))
    (def request (.getNewRequest core/message-factory))
    (def response (.getNewResponse core/message-factory))
    (.setRequestLine request "GET /make-move?space=1 HTTP/1.1")
    (get-response request response)
    (should-contain "422" (.getFormattedResponse response))))