(ns web-ttt.board-as-html-spec
  (:require [speclj.core :refer :all]
    [clojure.string :as str]
    [matts-clojure-ttt.board :as board]
    [web-ttt.board-as-html :refer :all]))

(describe "#render-board"
  (before-all
    (def empty-board (board/generate-new-board 3))
    (def rendered-empty-board (render-board empty-board "o")))

  (context "when given an empty 3x3 board and the marker o"
    (it "returns an empty 3x3 board"
      (should= (str/replace (slurp "resources/_empty_board_3.html") #"\s\s+" "") rendered-empty-board))

    (it "shows space 0 is playable by player o"
      (should-contain "<span class=\"space\" data-space=\"0\"><a href=\"/make-move?space=0&marker=o" rendered-empty-board)))

  (context "when given a board where player o has played on space 0"
    (it "returns a board with space 0 marked with o"
      (should-contain "<span class=\"marker\">o</span>" (render-board (board/mark-space empty-board 0 "o") "o")))))
