(ns game-of-life.game-spec
  (:require [speclj.core :refer :all]
            [game-of-life.game :refer :all]
            [game-of-life.util :refer :all]))

(describe "Conway's Game of Life"
  (it "kills cells with fewer than 2 live neighbors"
    (should= 0 (-> [[1 0]
                    [0 0]]
                 (next-state)
                 (get-cell 0 0)))

    (should= 0 (-> [[0 1]
                    [0 0]]
                 (next-state)
                 (get-cell 0 1)))

    (should= 0 (-> [[0 0]
                    [0 1]]
                 (next-state)
                 (get-cell 1 1))))

  (it "identifies the neighbors of a cell"
    (should=
      (repeat 8 0)
      (-> (empty-grid 3 3)
        (get-neighbors 1 1)))

    (should=
      '(1 0 0 0 0 0 0 1)
      (-> [[1 0 0]
           [0 0 0]
           [0 0 1]]
        (get-neighbors 1 1)))

    (should=
      (repeat 8 0)
      (-> (empty-grid 3 3)
        (get-neighbors 0 0))))

  (it "keeps a cell with 2 live neighbors"
    (should= 1
      (-> [[1 1 1]
           [0 0 0]]
        (next-state)
        (get-cell 0 1))))

  (it "keeps a cell with 3 live neighbors"
    (let [grid      [[1 1 1]
                     [0 1 0]]
          next-grid (next-state grid)]
      (should= 1 (get-cell next-grid 0 1))
      (should= 1 (get-cell next-grid 1 1))))

  (it "kills cells with more than 3 live neighbors"
    (let [grid      [[1 1 1]
                     [1 1 1]
                     [1 1 1]]
          next-grid (next-state grid)]
      (should= 0 (get-cell next-grid 1 1))
      (should= 0 (get-cell next-grid 0 1))
      (should= 0 (get-cell next-grid 1 0))
      (should= 0 (get-cell next-grid 1 2))
      (should= 0 (get-cell next-grid 2 1))))

  (it "creates a cell with exactly 3 live neighbors"
    (let [grid      [[1 0 1]
                     [0 0 0]
                     [1 0 0]]
          next-grid (next-state grid)]
      (should= 1 (get-cell next-grid 1 1))))

  (it "blinks"
    (let [grid [[0 0 0]
                [1 1 1]
                [0 0 0]]
          next-grid (next-state grid)]
      (should= (repeat 3 [0 1 0]) next-grid))))