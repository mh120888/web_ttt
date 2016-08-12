(ns web-ttt.board-state)

(def board-state (atom {}))

(defn- update-board-state-atom
  [collection-of-boards new-board]
  (conj collection-of-boards new-board))

(defn update-board
  [board]
  (reset! board-state board))

(defn get-board
  []
  @board-state)