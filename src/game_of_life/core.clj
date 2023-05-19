(ns game-of-life.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [game-of-life.util :refer :all]
            [game-of-life.game :refer [get-cell next-state]]
            [game-of-life.view :refer [draw-grid scale]]))

(declare life)

(def height 20)
(def width 20)
(def fps 5)

(defn setup []
  (q/frame-rate fps)
  (empty-grid height width))

(defn configure []
  [life
   [:size       [(* scale height) (* scale width)]]
   [:setup      setup]
   [:update     next-state]
   [:draw       draw-grid]
   [:middleware [m/fun-mode]]])

(defn toggle [grid x y]
  (update-in grid [x y] #(if (= 0 %) 1 0)))

(defn process-click []
  (let [x   (int (/ (q/mouse-x) scale))
        y   (int (/ (q/mouse-y) scale))]
    [x y]))

(defn new-state [state]
  (cond
    (q/mouse-pressed?) (let [[x y] (process-click)] (toggle state x y))
    (q/key-pressed?) (next-state state)
    :else state))

(defn game-of-life []
  (q/defsketch life
    :size   [(* scale height) (* scale width)]
    :setup  setup
    :update new-state
    :draw   draw-grid
    :middleware [m/fun-mode])
  #_(apply q/defsketch (apply concat (configure height width))))

(defn -main
  [& args]
  (game-of-life))
