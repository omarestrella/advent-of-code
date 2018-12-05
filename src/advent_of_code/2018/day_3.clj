(ns advent-of-code.2018.day-3
  (:require [clojure.string :as str]
            [clojure.set :refer [difference]]
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
    (->> (process-input input)
         (map #(get-coords (:pos %1) (:size %1)))
         (mapcat identity)
         (reduce (fn [seen coords]
                   (if (nil? (get seen coords))
                     (assoc seen coords 1)
                     (update seen coords inc)))
                 {})
         (filter #(> (second %) 1))
         (vals)
         (count)))

(defn part-2 [input]
  (let [data (->> (process-input input)
                  (map (fn [claim]
                         {:coords (get-coords (:pos claim) (:size claim))
                          :id     (:id claim)})))]
    (->> data
         (reduce (fn [coords-ids {coords :coords id :id}]
                   (loop [coords coords
                          coords-ids coords-ids]
                     (let [coord (first coords)]
                       (if (nil? coord)
                         coords-ids
                         (if (nil? (get coords-ids coord))
                           (recur (rest coords) (assoc coords-ids coord [id]))
                           (recur (rest coords) (update coords-ids coord conj id)))))))
                 {})
         (vals)
         (filter #(> (count %) 1))
         (flatten)
         (set)
         (difference (set (map :id data)))
         (first)
         (util/parse-int))))

(defn run [input]
  [(part-1 input) (part-2 input)])
