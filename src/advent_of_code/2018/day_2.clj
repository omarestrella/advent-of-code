(ns advent-of-code.2018.day-2
  (:require [clojure.string :as str]))

(defn split-chars [string]
  (str/split string #""))

(defn char-counts [seen char]
  (if (get seen char)
    (into seen {char (inc (get seen char))})
    (into seen {char 1})))

(defn part-1 [input]
  (->> (str/split-lines input)
       (map split-chars)
       (map (fn [chars]
              (reduce char-counts {} chars)))
       (map (fn [counts]
              (->> (vals counts)
                   (set)
                   (filter #(and (> %1 1) (< %1 4))))))
       (flatten)
       (reduce char-counts {})
       (vals)
       (apply *)))

(defn part-2 [input]
  0)

(defn run [input]
  [(part-1 input) (part-2 input)])
