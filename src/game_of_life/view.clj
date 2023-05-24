(ns game-of-life.view
  (:require
    [quil.core :as q :include-macros true]
    [game-of-life.util :refer :all]))

(def scale 20)

(defn draw-cell [x y]
  (let [color 0]
    (q/fill color)
    (q/rect (* scale x) (* scale y) scale scale)))

(defn draw-cells [cells]
  (q/background 255)
  (doall (map #(draw-cell (first %) (last %)) cells)))