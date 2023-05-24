(ns game-of-life.game
  (:require [clojure.set :as set]
            [game-of-life.util :refer :all]))

(def neighbor-range 1.5)

(defn sqr [n]
  (* n n))

(defn calc-distance [cell1 cell2]
  (let [[x1 y1] cell1 [x2 y2] cell2]
    (Math/sqrt (+ (sqr (- x2 x1)) (sqr (- y2 y1))))))

(defn neighbor? [cell1 cell2]
  (<= (calc-distance cell1 cell2) neighbor-range))

(defn get-neighbors [cells cell]
  (let [other-cells (disj cells cell)]
    (filter #(neighbor? cell %) other-cells)))

(defn stays-alive? [cells cell]
  (<= 2 (count (get-neighbors cells cell)) 3))

(defn comes-alive? [cells cell]
  (= 3 (count (get-neighbors cells cell))))

(defn pt-range [vals]
  (range (dec (apply min vals)) (+ 2 (apply max vals))))

(defn possible-cells [cells]
  (let [x-vals (map first cells)
        y-vals (map last cells)]
    (for [x (pt-range x-vals)
          y (pt-range y-vals)]
      (list x y))))

(defn new-cells [cells]
  (if (empty? cells)
    #{}
    (->> (possible-cells cells)
      (filter #(comes-alive? cells %))
      (set))))

(defn next-state [state]
  (->> (filter #(stays-alive? state %) state)
    (set)
    (set/union (new-cells state))))