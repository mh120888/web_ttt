(ns web-ttt.board-state
  (:require [matts-clojure-ttt.board :as board]))

(def board-turn (atom ""))

(defn change-turn
  []
  (swap! board-turn board/get-other-marker))

(defn set-turn
  [marker]
  (reset! board-turn marker))

(def board-state (atom {}))

(defn update-board
  [board]
  (reset! board-state board))

(defn get-board
  []
  @board-state)