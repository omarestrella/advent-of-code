(ns advent-of-code.2018.day-6
  (:require [clojure.string :as str]
            [advent-of-code.util :as util]))

(def test-input "1, 1\n1, 6\n8, 3\n3, 4\n5, 5\n8, 9")

(defn abs [n]
  (Math/abs n))

(defn grid [w h]
  (into [] (repeat w (into [] (repeat h 0)))))

(defn grid-points [grid]
  (let [height (count grid)
        width (count (first grid))]
    (for [x (range width) y (range height)] [x y])))

(defn mapped-grid [grid]
  (->> grid
       (map-indexed vector)
       (into (sorted-map))))

(defn manhattan [[p1 p2] [q1 q2]]
  (+ (abs (- p1 q1))
     (abs (- p2 q2))))

(defn points [input]
  (->> (str/split-lines input)
       (map #(str/split % #", "))
       (map #(into [] (map util/parse-int) %))))

(defn eliminate-edges [max-x max-y data]
  (let [points-to-eliminate (->> data
                                 (filter (fn [d]
                                           (let [[x y] (:grid-point d)]
                                             (or (= x 0)
                                                 (= y 0)
                                                 (= x max-x)
                                                 (= y max-y)))))
                                 (map :point)
                                 (set))]
    (filter (fn [d]
              (not (contains? points-to-eliminate (:point d))))
            data)))

(defn part-1 [input]
  (let [points (points input)
        max-x (apply max (map first points))
        max-y (apply max (map second points))
        grid (grid (+ 1 max-x) (+ 1 max-y))
        grid-points (grid-points grid)]
    (->> grid-points
         (reduce (fn [data grid-point]
                   (let [distances (map (fn [point]
                                          {:grid-point grid-point
                                           :point      point
                                           :distance   (manhattan grid-point point)}) points)
                         closest (first (sort-by :distance distances))]
                     (conj data closest)))
                 [])
         (eliminate-edges max-x max-y)
         (clojure.pprint/pprint))))
         ;(group-by :point)
         ;(reduce (fn [acc [_ v]]
         ;          (if (> (count v) acc)
         ;            (count v)
         ;            acc))
         ;        0))))

(defn part-2 [input]
  0)

(defn run [input]
  [(part-1 input) (part-2 input)])
