(ns game-of-life.view
  (:require
    [quil.core :as q :include-macros true]
    [game-of-life.util :refer :all]
    [game-of-life.game :refer [get-cell]]))

(def scale 20)

(defn draw-cell [grid x y]
  (let [cell  (get-cell grid x y)
        color (if (= 1 cell) 0 255)]
    (q/fill color)
    (q/rect (* scale x) (* scale y) scale scale)))

(defn draw-grid [grid]
  (doall (for-cell grid draw-cell)))