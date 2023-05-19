(ns game-of-life.util)

(defn empty-grid [rows cols]
  (vec (repeat rows (vec (repeat cols 0)))))

(defn full-grid [rows cols]
  (vec (repeat rows (vec (repeat cols 1)))))

(defn get-width [grid]
  (count (first grid)))

(defn get-cell
  ([grid x y]
   (nth (nth grid x 0) y 0))
  ([grid pt]
   (nth (nth grid (first pt) []) (last pt) 0)))

(defn for-cell [grid f]
  (let [rows      (count grid)
        cols      (get-width grid)]
    (for [x (range rows) y (range cols)]
      (f grid x y))))