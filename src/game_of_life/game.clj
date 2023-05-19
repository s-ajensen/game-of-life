(ns game-of-life.game
  (:require [game-of-life.util :refer :all]))

(defn get-cell
  ([grid x y]
   (nth (nth grid x 0) y 0))
  ([grid pt]
   (nth (nth grid (first pt) []) (last pt) 0)))

(defn get-neighbors
  [grid row col]
  (let [offsets '(-1 0 1)
        neighbors (for [x offsets y offsets]
                    (if (not (and (= 0 x) (= 0 y)))
                      (list (+ row x) (+ col y))))]
    (->> neighbors
      (remove nil?)
      (map #(get-cell grid %)))))

(defn alive? [cell neighbors]
  (cond
    (= 1 cell) (if (or (< 3 neighbors) (> 2 neighbors)) 0 1)
    :else (if (= 3 neighbors) 1 0)))

(defn cell-val [grid x y]
  (let [cell      (get-cell grid x y)
        neighbors (reduce + (get-neighbors grid x y))]
    (alive? cell neighbors)))

(defn next-state
  [grid]
  (->> (for-cell grid cell-val)
    (partition (get-width grid))
    (map vec)
    (vec)))