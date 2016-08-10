(ns web-ttt.board-as-html-spec
  (:require [speclj.core :refer :all]
    [clojure.string :as str]
    [matts-clojure-ttt.board :as board]
    [web-ttt.board-as-html :refer :all]))

(describe "#render-board"
  (before-all
    (def empty-board (board/generate-new-board 3))
    (def rendered-empty-board (render-board empty-board "o")))

  (it "returns an empty 3x3 board when given a new board"
    (should= (str/replace (slurp "resources/_empty_board_3.html") #"\s\s+" "") rendered-empty-board))

  (it "returns a board with 0 marked when 0 had been played"
    (should-contain "<span class=\"marker\">o</span>" (render-board (board/mark-space empty-board 0 "o") "o"))))
