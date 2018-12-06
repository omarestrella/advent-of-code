(ns advent-of-code.core
  (:gen-class)
  (:require [advent-of-code.util :as util]
            [advent-of-code.2018.day-5 :refer [run]]))

(defn run-day []
  (let [input (->> (util/get-input 2018 5))
        [part-1 part-2] (run input)]
    (println "Part 1:" part-1)
    (println "Part 2:" part-2)))

(defn -main [& args]
  (run-day))


