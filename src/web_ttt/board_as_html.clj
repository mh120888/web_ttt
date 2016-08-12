(ns web-ttt.board-as-html
  (:require [matts-clojure-ttt.game :as game]
    [hiccup.core :as hiccup]
    [matts-clojure-ttt.board :as board]))

(def empty-space "__")

(defn- generate-path-for-link
  [board space marker]
  (str "/make-move?space=" space "&" "marker=" marker))

(defn- render-open-space
  [board space marker winner]
  (if (nil? winner)
    (clojure.string/replace (hiccup/html [:a {:href (generate-path-for-link board space marker)} empty-space]) "&amp;" "&")
    (hiccup/html [:span {:class "marker"} empty-space])))

(defn- render-taken-space
  [board space]
  (hiccup/html [:span {:class "marker"} (board/look-up-space board space)]))

(defn render-space
  [board space marker winner]
  (hiccup/html [:span {:class "space" :data-space space}
    (if (board/space-free? board space)
      (render-open-space board space marker winner)
      (render-taken-space board space))]))

(defn render-row
  [board row num-of-rows marker winner]
  (hiccup/html [:div {:class "row"}
    (for [column (range 0 num-of-rows)]
      (render-space board (+ column (* row num-of-rows)) marker winner))]))

(defn render-board
  [board marker]
  (let [num-of-rows (board/memoize-get-number-of-rows board)
        winner (board/get-winner board)]
    (hiccup/html [:div {:id "board"}
      (for [row (range 0 num-of-rows)]
        (render-row board row num-of-rows marker winner))])))

(defn render-alert
  [board]
  (let [winner (board/get-winner board)]
    (cond
      (not (nil? winner)) (hiccup/html [:p {:class "alert"} "Game Over - Player " winner " won"])
      (board/board-full? board) (hiccup/html [:p {:class "alert"} "Game Over - Cat's Game"])
      :else (hiccup/html [:a {:href "/computer-move"} "Get Computer Move"]))))

(defn generate-html-response
  [board marker]
  (let [board-as-html (render-board board marker)
        alert-message (render-alert board)]
    (str board-as-html alert-message)))
