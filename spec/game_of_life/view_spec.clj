(ns game-of-life.view-spec
  (:require [quil.core :as q]
            [speclj.core :refer :all]
            [speclj.stub :as stub]
            [game-of-life.view :refer :all]
            [game-of-life.util :refer :all]))

(declare empty)
(declare full)
(defn get-coords [grid x y] [(* scale x) (* scale y) scale scale])

(describe "Conway's Game of Life UI"

  (with-stubs)
  (with empty   (empty-grid 3 3))
  (with full    (full-grid 3 3))

  (redefs-around [q/rect (stub :mock-rect)
                  q/fill (stub :mock-fill)])

  (it "draws a rectangle for every cell in the grid"
    (draw-grid @empty)
    (should-have-invoked :mock-rect {:times 9}))

  (it "positions each cell with respect to its coordinate"
    (draw-grid @empty)
    (doseq [coord (for-cell @empty get-coords)]
      (should-have-invoked :mock-rect {:with coord})))

  (it "colors alive cells black"
    (draw-grid @full)
    (doseq [cell (flatten @full)]
      (should-have-invoked :mock-fill {:with [0]})))

  (it "colors dead cells white"
    (draw-grid @empty)
    (doseq [cell (flatten @empty)]
      (should-have-invoked :mock-fill {:with [255]}))))