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
  (hiccup/html [:span {:class "space"}
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

(def play-again-link (hiccup/html [:a {:href "/"} "Do you want to play again?"]))

(defn- render-game-in-progress-humans-turn [] "")

(defn- render-game-in-progress-computers-turn []
  (hiccup/html [:a {:class "computer-move" :href "/computer-move"} "Get Computer Move"]))

(defn- render-cats-game [] (hiccup/html [:p {:class "alert"} (str "Game Over - Cat's Game" play-again-link)]))

(defn- render-game-over-with-winner
  [winner]
  (hiccup/html [:p {:class "alert"} (str "Game Over - Player " winner " won" play-again-link)]))

(defn render-alert
  [board turn]
  (let [winner (board/get-winner board)]
    (cond
      winner (render-game-over-with-winner winner)
      (board/board-full? board) (render-cats-game)
      (= turn :computers-turn) (render-game-in-progress-computers-turn)
      :else (render-game-in-progress-humans-turn))))

(defn generate-html-response
  [board marker turn]
  (let [board-as-html (render-board board marker)
        alert-message (render-alert board turn)]
    (str board-as-html alert-message)))
