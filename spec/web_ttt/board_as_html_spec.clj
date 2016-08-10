(ns web-ttt.board-as-html-spec
  (:require [speclj.core :refer :all]
    [clojure.string :as str]
    [matts-clojure-ttt.board :as board]
    [web-ttt.board-as-html :refer :all]))

(describe "#render-board"
  (it "returns an empty 3x3 board when given a new board"
    (should= (str/replace (slurp "resources/_empty_board_3.html") #"\s\s+" "") (render-board (board/generate-new-board 3)))))
