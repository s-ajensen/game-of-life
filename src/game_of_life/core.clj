(ns game-of-life.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [game-of-life.util :refer :all]
            [game-of-life.game :refer [next-state]]
            [game-of-life.view :refer [draw-cells scale]]))

(declare life)

(def height 20)
(def width 20)
(def fps 5)

(defn setup []
  (q/frame-rate fps)
  #{})

(defn toggle [cells x y]
  (let [pt (list x y)]
    (if (contains? cells pt)
      (disj cells pt)
      (conj cells pt))))

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
    :draw   draw-cells
    :middleware [m/fun-mode]))

(defn -main
  [& args]
  (game-of-life))
