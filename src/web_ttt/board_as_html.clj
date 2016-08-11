(ns web-ttt.board-as-html
  (:require [matts-clojure-ttt.game :as game]
    [hiccup.core :as hiccup]
    [matts-clojure-ttt.board :as board]))

(defn- generate-path-for-link
  [board space marker]
  (str "/make-move?space=" space "&" "marker=" marker "&" "board=" (clojure.string/replace board #"\s" "")))

(defn- render-open-space
  [board space marker]
  (clojure.string/replace (hiccup/html [:a {:href (generate-path-for-link board space marker)} "__"]) "&amp;" "&"))

(defn- render-taken-space
  [board space]
  (hiccup/html [:span {:class "marker"} (board/look-up-space board space)]))

(defn render-space
  [board space marker]
  (hiccup/html [:span {:class "space" :data-space space}
    (if (board/space-free? board space)
      (render-open-space board space marker)
      (render-taken-space board space))]))

(defn render-row
  [board row num-of-rows marker]
  (hiccup/html [:div {:class "row"}
    (for [column (range 0 num-of-rows)]
      (render-space board (+ column (* row num-of-rows)) marker))]))

(defn render-board
  [board marker]
  (let [num-of-rows (board/memoize-get-number-of-rows board)]
    (hiccup/html [:div {:id "board"}
      (for [row (range 0 num-of-rows)]
        (render-row board row num-of-rows marker))])))

(defn render-alert
  [board]
  (if (board/board-full? board)
    (hiccup/html [:p {:class "alert"} "Game Over - Cat's Game"])
    ""))

(defn generate-html-response
  [board marker]
  (let [board-as-html (render-board board marker)
    alert-message (render-alert board)]
    (str board-as-html alert-message)))
