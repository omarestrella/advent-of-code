(ns advent-of-code.2018.day-3
  (:require [clojure.string :as str]
            [advent-of-code.util :as util]))

(defn process-input [input]
  (->> input
       (str/split-lines)
       (map #(->> (re-find #"\#(\d+)[\s@]+([\d,]+):\s([\dx]+)" %1)
                  (rest)
                  (apply (fn [id pos size]
                           [id (map util/parse-int (str/split pos #",")) (map util/parse-int (str/split size #"x"))]))
                  (zipmap [:id :pos :size])))))

(defn get-coords [[x-pos y-pos] [x-size y-size]]
  (for [x (range x-pos (+ x-pos x-size))
        y (range y-pos (+ y-pos y-size)) :while (or (< x (+ x-pos x-size)) (< y (+ y-pos y-size)))]
    (vector x y)))

(defn part-1 [input]
  (let [grid (make-array Integer/TYPE 1000 1000)]
    (->> (process-input input)
         (map #(get-coords (:pos %1) (:size %1)))
         (mapcat identity)
         (reduce (fn [results [x y]]
                   (let [value (aget grid x y)
                         new-value (aset-int grid x y (inc value))]
                     (if (> new-value 1)
                       (conj results [x y])
                       results)))
                 #{})
         (count))))

(defn part-2 [input]
  (let [input] (process-input "#1 @ 1,3: 4x4\n#2 @ 3,1: 4x4\n#3 @ 5,5: 2x2"))
  0)

(defn run [input]
  [(part-1 input) (part-2 input)])
