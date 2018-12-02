(ns advent-of-code.2018.day-2
  (:require [clojure.string :as str]
            [clojure.data :refer [diff]]
            [advent-of-code.util :as util]))

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

(defn different-by-1? [[word-1 word-2]]
  (let [[only-in-1
         only-in-2
         in-both] (diff (split-chars word-1) (split-chars word-2))]
    (if (nil? in-both)
      false
      (and (= 1 (count (remove nil? only-in-1)))
           (= 1 (count (remove nil? only-in-2)))))))

(defn same-characters [[word-1 word-2]]
  (let [[_ _
         in-both] (diff (split-chars word-1) (split-chars word-2))]
    (str/join "" (remove nil? in-both))))

(defn part-2 [input]
  (let [words (str/split-lines input)]
    (->> (for [x words y words] (vector x y))
         (filter #(different-by-1? %1))
         (first)
         (same-characters))))

(defn run [input]
  [(part-1 input) (part-2 input)])
