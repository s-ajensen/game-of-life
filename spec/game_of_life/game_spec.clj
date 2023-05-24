(ns game-of-life.game-spec
  (:require [speclj.core :refer :all]
            [game-of-life.game :refer :all]
            [game-of-life.util :refer :all]))

(defn test-neighbors [cells cell neighbors]
  (should= neighbors (get-neighbors cells cell)))

(describe "conway's game of life logic"
  (it "identifies adjacent neighbor of a cell"
    (test-neighbors
      #{'(0 0) '(0 1)}
      '(0 0)
      '((0 1))))

  (it "identifies multiple adjacent neighbors of a cell"
    (test-neighbors
      #{'(0 0) '(0 1) '(0 2)}
      '(0 1)
      '((0 0) (0 2))))

  (it "calculates the distance between cells"
    (should= 1.0 (calc-distance '(0 0) '(0 1)))
    (should= 2.0 (calc-distance '(0 0) '(0 2)))
    (should= (Math/sqrt 2) (calc-distance '(0 0) '(1 1))))

  (it "does not identify non-neighbors of a cell"
    (test-neighbors
      #{'(0 0) '(0 1) '(0 3)}
      '(0 1)
      '((0 0))))

  (it "identifies diagonal neighbors of a cell"
    (test-neighbors
      #{'(0 0) '(0 2) '(1 1)}
      '(1 1)
      '((0 0) (0 2))))

  (it "kills live cells with fewer than 2 neighbors"
    (should-not-contain '(0 0) (next-state #{'(0 0)}))
    (should-not-contain '(0 0) (next-state #{'(0 0) '(0 2)})))

  (it "keeps live cells with 2 neighbors alive"
    (should-contain '(0 0) (next-state #{'(0 0) '(0 1) '(1 0)}))
    (should-contain '(1 1) (next-state #{'(0 0) '(0 2) '(1 1)})))

  (it "keeps live cells with 3 neighbors alive"
    (should-contain '(1 1) (next-state #{'(0 0) '(0 1) '(0 2) '(1 1)})))

  (it "kills live cells with more than 3 neighbors"
    (should-not-contain '(1 1) (next-state #{'(0 0) '(0 1) '(0 2) '(1 0) '(1 1)})))

  (it "identifies possibly alive cells on the board"
    (let [possible (possible-cells #{'(0 0) '(0 3)})]
      (should-contain '(0 1) possible)
      (should-contain '(0 2) possible))
    (let [possible (possible-cells #{'(0 0) '(0 2) '(1 0)})]
      (should-contain '(0 1) possible)
      (should-contain '(1 0) possible)
      (should-contain '(1 1) possible)
      (should-contain '(1 2) possible)))

  (it "brings dead cells with 3 neighbors to life"
    (should-contain '(1 1) (next-state #{'(0 0) '(0 2) '(2 0)}))
    (let [new-state (next-state #{'(1 0) '(1 1) '(1 2)})]
      (should-contain '(0 1) new-state)
      (should-contain '(2 1) new-state))))