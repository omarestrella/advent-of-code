(ns advent-of-code.core
  (:gen-class)
  (:require [clojure.string :as str]
            [advent-of-code.util :as util]
            [advent-of-code.2018.day-1 :as day-1]))

(defn run-day-1 []
  (let [input (->> (util/get-input 2018 1)
                   (str/split-lines)
                   (map util/parse-int))
        [part-1 part-2] (day-1/run input)]
    (println "Part 1:" part-1) ()
    (println "Part 2:" part-2)))

(defn -main [& args]
  (run-day-1))


