(ns game-of-life.core-spec
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [speclj.core :refer :all]
            [game-of-life.core :refer :all]
            [game-of-life.game :refer [next-state]]
            [game-of-life.view :refer [draw-grid scale]]
            [game-of-life.util :refer :all]))

(declare configuration)

(def blinker [[0 1 0] [0 1 0] [0 1 0]])

(describe "Conway's Game of Life control loop tests"

  (with-stubs)
  (redefs-around [q/defsketch (stub :mock-defsketch)
                  q/frame-rate (stub :mock-frame-rate)
                  q/mouse-pressed? (stub :mock-mouse-pressed? {:return true})
                  q/mouse-x (stub :mock-mouse-x {:return 43})
                  q/mouse-y (stub :mock-mouse-y {:return 43})
                  m/fun-mode (stub :mock-fun-mode)])
  (with configuration (configure))

  (it "configures window size"
    (should-contain [:size [(* scale height) (* scale width)]] @configuration))

  (it "configures setup"
    (should-contain [:setup setup] @configuration))

  (it "configures updater"
    (should-contain [:update next-state] @configuration))

  (it "configures renderer"
    (should-contain [:draw draw-grid] @configuration))

  (it "configures quil functional mode"
    (should-contain [:middleware [m/fun-mode]] @configuration))

  (it "setup sets frame rate"
    (setup)
    (should-have-invoked :mock-frame-rate {:with [fps]}))

  (it "setup initializes empty grid"
    (should= (empty-grid height width) (setup)))

  (it "toggles the state of a cell"
    (let [empty-grid  (empty-grid 3 3)
          full-grid   (full-grid 3 3)]
      (should-contain 1 (flatten (toggle empty-grid 0 0)))
      (should-contain 0 (flatten (toggle full-grid 1 1)))))

  (it "calculates closest cell to mouse click"
    (should=[2 2] (process-click)))

  (it "toggles selected cell when mouse is clicked"
    (should-contain 1 (flatten (new-state (empty-grid 3 3)))))

  #_(it "updates the game state if key is pressed"
    (with-redefs [q/mouse-pressed? (stub :mock-mouse-pressed? {:return false})
                  q/key-pressed? (stub :mock-key-pressed? {:return true})
                  toggle (stub :mock-toggle)]
      (let [grid [[0 0 0] [1 1 1] [0 0 0]]]
        (should-not-have-invoked :mock-toggle)
        (should= grid (new-state blinker)))))

  (it "does not update the game if key is not pressed"
    (with-redefs [q/mouse-pressed? (stub :mock-mouse-pressed? {:return false})
                  q/key-pressed? (stub :mock-key-pressed? {:return false})
                  toggle (stub :mock-toggle)
                  next-state (stub :mock-next-state)])
    (let [grid [[0 0 0] [1 1 1] [0 0 0]]]
      (new-state grid)
      (should-not-have-invoked :mock-toggle)
      (should-not-have-invoked :mock-next-state))))