(ns web-ttt.board-as-html
  (:require [matts-clojure-ttt.game :as game]
    [hiccup.core :as hiccup]
    [matts-clojure-ttt.board :as board]))

(defn render-board
  [board]
  (let [num-of-rows (board/memoize-get-number-of-rows board)]
    (hiccup/html [:div {:id "board"}
      (for [x (range 0 num-of-rows)]
         [:div {:class "row"}
          (for [y (range 0 num-of-rows)]
            [:span {:class "space" :data-space (+ y (* x num-of-rows))} [:a {:href (str "/make-move?space=" (+ y (* x num-of-rows)))} "__"]])])])))