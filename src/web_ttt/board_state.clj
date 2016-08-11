(ns web-ttt.board-state)

(def board-states (atom []))

(defn- update-board-state-atom
  [collection-of-boards new-board]
  (conj collection-of-boards new-board))

(defn update-board
  [board]
  (swap! board-states update-board-state-atom board))

(defn get-board
  []
  (last @board-states))