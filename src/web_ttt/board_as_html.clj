(ns web-ttt.board-as-html
  (:require [matts-clojure-ttt.game :as game]
    [hiccup.core :as hiccup]
    [matts-clojure-ttt.board :as board]))

(defn- render-space
  [board space]
    (hiccup/html [:span {:class "space" :data-space space}
    (if (board/space-free? board space)
      (hiccup/html [:a {:href (str "/make-move?space=" space)} "__"])
      (hiccup/html [:span {:class "marker"} (board/look-up-space board space)]))]))

(defn- render-row
  [board row num-of-rows]
  (hiccup/html [:div {:class "row"}
    (for [column (range 0 num-of-rows)]
      (render-space board (+ column (* row num-of-rows))))]))

(defn render-board
  [board]
  (let [num-of-rows (board/memoize-get-number-of-rows board)]
    (hiccup/html [:div {:id "board"}
      (for [row (range 0 num-of-rows)]
        (render-row board row num-of-rows))])))
