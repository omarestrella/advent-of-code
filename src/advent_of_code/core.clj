(ns advent-of-code.core
  (:gen-class)
  (:require [clojure.string :as str]
            [advent-of-code.util :as util]
            [advent-of-code.2018.day-1 :as day-1]
            [advent-of-code.2018.day-2 :as day-2]
            [advent-of-code.2018.day-3 :as day-3]))

(defn run-day-1 []
  (let [input (->> (util/get-input 2018 1)
                   (str/split-lines)
                   (map util/parse-int))
        [part-1 part-2] (day-1/run input)]
    (println "Part 1:" part-1)
    (println "Part 2:" part-2)))

(defn run-day-2 []
  (let [input (->> (util/get-input 2018 2))
        [part-1 part-2] (day-2/run input)]
    (println "Part 1:" part-1)
    (println "Part 2:" part-2)))

(defn run-day-3 []
  (let [input (->> (util/get-input 2018 3))
        [part-1 part-2] (day-3/run input)]
    (println "Part 1:" part-1)
    (println "Part 2:" part-2)))

(defn -main [& args]
  (run-day-3))


