(defproject game-of-life "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main game-of-life.core
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [quil "3.1.0"]]
  :profiles {:dev {:dependencies [[speclj "3.4.3"]]}}
  :plugins [[speclj "3.4.3"]]
  :test-paths ["spec"]
  :java-cmd "/Library/Java/JavaVirtualMachines/jdk-1.8.jdk/Contents/Home/bin/java")
