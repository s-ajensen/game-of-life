(ns game-of-life.core-spec
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [speclj.core :refer :all]
            [game-of-life.core :refer :all]
            [game-of-life.game :refer [next-state]]
            [game-of-life.view :refer [draw-cells scale]]
            [game-of-life.util :refer :all]))

(def blinker #{'(0 1) '(1 1) '(2 1)})

(describe "Conway's Game of Life control loop tests"

  (with-stubs)
  (redefs-around [q/defsketch (stub :mock-defsketch)
                  q/frame-rate (stub :mock-frame-rate)
                  q/mouse-pressed? (stub :mock-mouse-pressed? {:return true})
                  q/mouse-x (stub :mock-mouse-x {:return 43})
                  q/mouse-y (stub :mock-mouse-y {:return 43})
                  m/fun-mode (stub :mock-fun-mode)])

  #_(it "defines the board sketch"
    (game-of-life)
    (should-have-invoked :mock-defsketch {:with [life
                                                 :size [(* scale height) (* scale width)]
                                                 :setup setup
                                                 :update next-state
                                                 :draw draw-cells
                                                 :middleware [m/fun-mode]]}))

  (it "setup sets frame rate"
    (setup)
    (should-have-invoked :mock-frame-rate {:with [fps]}))

  (it "setup initializes empty cell set"
    (should= #{} (setup)))

  (it "toggles the state of a cell"
    (let [populated #{'(0 0)}
          empty #{}]
      (should (empty? (toggle populated 0 0)))
      (should (some? (toggle empty 0 0)))))

  (it "calculates closest cell to mouse click"
    (should=[2 2] (process-click)))

  (it "toggles selected cell when mouse is clicked"
    (should-contain '(2 2) (new-state #{})))

  #_(it "updates the game state if key is pressed"
    (with-redefs [q/mouse-pressed? (stub :mock-mouse-pressed? {:return false})
                  q/key-pressed? (stub :mock-key-pressed? {:return true})
                  toggle (stub :mock-toggle)]
      (let [grid #{'(1 0) '(1 1) '(1 2)}]
        (should-not-have-invoked :mock-toggle)
        (should= grid (new-state blinker)))))

  (it "does not update the game if key is not pressed"
    (with-redefs [q/mouse-pressed? (stub :mock-mouse-pressed? {:return false})
                  q/key-pressed? (stub :mock-key-pressed? {:return false})
                  toggle (stub :mock-toggle)
                  next-state (stub :mock-next-state)])
    (let [grid #{'(1 0) '(1 1) '(1 2)}]
      (new-state grid)
      (should-not-have-invoked :mock-toggle)
      (should-not-have-invoked :mock-next-state))))