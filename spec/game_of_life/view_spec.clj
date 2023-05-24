(ns game-of-life.view-spec
  (:require [quil.core :as q]
            [speclj.core :refer :all]
            [game-of-life.view :refer :all]
            [game-of-life.util :refer :all]))

(declare cells)
(defn get-rect [x y] [(* scale x) (* scale y) scale scale])

(describe "Conway's Game of Life UI"

  (with-stubs)
  (with cells '((0 0) (1 1) (2 2) (3 3)))

  (redefs-around [q/rect (stub :mock-rect)
                  q/fill (stub :mock-fill)])

  (it "draws a rectangle for every live cell"
    (draw-cells @cells)
    (should-have-invoked :mock-rect {:times 4}))

  (it "positions each cell with respect to its coordinate"
    (draw-cells @cells)
    (doseq [coord @cells]
      (should-have-invoked :mock-rect {:with (get-rect (first coord) (last coord))})))

  (it "colors alive cells black"
    (draw-cells @cells)
    (doseq [cell (flatten @cells)]
      (should-have-invoked :mock-fill {:with [0]}))))