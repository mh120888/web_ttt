(ns web-ttt.board-as-html-spec
  (:require [speclj.core :refer :all]
    [clojure.string :as str]
    [matts-clojure-ttt.board :as board]
    [web-ttt.board-as-html :refer :all]))

(describe "#render-board"
  (before-all
    (def empty-board (board/generate-new-board 3))
    (def board-in-progress {0 {:marked "x"}, 1{}, 2{}, 3{}, 4 {:marked "o"}, 5{}, 6{}, 7{}, 8{}})
    (def board-with-cats-game {0 {:marked "x"}, 1{:marked "o"}, 2{:marked "x"}, 3{:marked "o"}, 4 {:marked "x"}, 5{:marked "o"}, 6{:marked "o"}, 7{:marked "x"}, 8{:marked "o"}})
    (def board-won-by-x {0 {:marked "x"}, 1{:marked "x"}, 2{:marked "x"}, 3{:marked "o"}, 4 {}, 5{:marked "o"}, 6{}, 7{:marked "o"}, 8{:marked "x"}})
    (def rendered-empty-board (render-board empty-board "o")))

  (context "when given an empty 3x3 board and the marker o"
    (it "returns an empty 3x3 board"
      (should= (str/replace (slurp "resources/_empty_board_3.html") #"\s\s+" "") rendered-empty-board))

    (it "shows space 0 is playable by player o"
      (should-contain "<span class=\"space\"><a href=\"/make-move?space=0&marker=o" rendered-empty-board)))

  (context "when given a board where player o has played on space 0"
    (it "returns a board with space 0 marked with o"
      (should-contain "<span class=\"marker\">o</span>" (render-board (board/mark-space empty-board 0 "o") "o"))))

  (context "when given a board with a winner and open spaces"
    (it "returns a board with no playable spaces (even empty spaces are not playable because the game is over)"
      (should-not-contain "<a href=/make-move" (render-board board-won-by-x "x")))))

(describe "#render-alert"
  (it "returns a link to get computer move for an empty board when the computer player goes first"
    (should-contain "Get Computer Move" (render-alert empty-board :computers-turn)))

  (it "returns an empty string for an empty board when the computer player goes second"
    (should= "" (render-alert empty-board :humans-turn)))

  (it "returns a link to get computer move for a game that is not yet over (no winner, no tie)"
    (should-contain "Get Computer Move" (render-alert board-in-progress :computers-turn)))

  (it "returns an empty string for a board in progress when it is not the computer's turn"
    (should= "" (render-alert board-in-progress :humans-turn)))

  (it "includes the phrase \"Cat's Game\" in the response for a tied game"
    (should-contain "Cat's Game" (render-alert board-with-cats-game false)))

  (it "includes a link to play a new game in the response for a tied game"
    (should-contain "<a href=\"/\">" (render-alert board-with-cats-game false)))

  (it "includes the phrase \"Player x won\" in the response for a game won by player x"
    (should-contain "Player x won" (render-alert board-won-by-x false)))

  (it "includes a link to play a new game in the response for a game with a winner"
    (should-contain "<a href=\"/\">" (render-alert board-won-by-x false))))
