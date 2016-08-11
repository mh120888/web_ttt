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
    (def board-won-by-x {0 {:marked "x"}, 1{:marked "x"}, 2{:marked "x"}, 3{:marked "o"}, 4 {:marked "x"}, 5{:marked "o"}, 6{:marked "x"}, 7{:marked "o"}, 8{:marked "x"}})
    (def rendered-empty-board (render-board empty-board "o")))

  (context "when given an empty 3x3 board and the marker o"
    (it "returns an empty 3x3 board"
      (should= (str/replace (slurp "resources/_empty_board_3.html") #"\s\s+" "") rendered-empty-board))

    (it "shows space 0 is playable by player o"
      (should-contain "<span class=\"space\" data-space=\"0\"><a href=\"/make-move?space=0&marker=o" rendered-empty-board)))

  (context "when given a board where player o has played on space 0"
    (it "returns a board with space 0 marked with o"
      (should-contain "<span class=\"marker\">o</span>" (render-board (board/mark-space empty-board 0 "o") "o")))))

(describe "#render-alert"
  (it "returns an empty string for an empty board"
    (should= "" (render-alert empty-board)))

  (it "returns an empty string for a game that is not yet over (no winner, no tie)"
    (should= "" (render-alert board-in-progress)))

  (it "includes the phrase \"Cat's Game\" in the response for a tied game"
    (should-contain "Cat's Game" (render-alert board-with-cats-game)))

  (it "includes the phrase \"Player x won\" in the response for a game won by player x"
    (should-contain "Player x won" (render-alert board-won-by-x))))
