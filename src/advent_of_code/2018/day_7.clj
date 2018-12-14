(ns advent-of-code.2018.day-7
  (:require [clojure.string :as str]
            [clojure.zip :as zip]
            [clojure.set :as set]))

(def test-input "Step A must be finished before step B can begin.\nStep A must be finished before step D can begin.\nStep C must be finished before step A can begin.\nStep D must be finished before step E can begin.\nStep B must be finished before step E can begin.\nStep C must be finished before step F can begin.\nStep F must be finished before step E can begin.")

(defn all-steps [input]
  (->> (str/split-lines input)
       (map #(rest (re-find #"Step (\w) must be finished before step (\w) can begin" %)))
       (flatten)
       (set)))

(defn step-data [entry]
  (->> entry
       (re-find #"Step (\w) must be finished before step (\w) can begin")
       (rest)
       (apply (fn [a b] {b [a]}))))

(defn parse-input [input]
  (->> (str/split-lines input)
       (map step-data)
       (apply merge-with into)))

(defn step-time [step]
  (+ 60 (- (int (first step)) 64)))

; not used, but wanna keep the dfs for later...maybe
(defn traverse [graph start]
  (-> (loop [vertices []
             discovered #{start}
             children [start]]
        (if (empty? children)
          vertices
          (let [node (peek children)
                adj-edges (get graph node)]
            (recur
              (conj vertices node)
              (into discovered adj-edges)
              (into (pop children) (remove discovered adj-edges))))))))

(defn part-1 [input]
  (let [data (parse-input input)
        steps (all-steps input)]
    (->> (loop [data data
                steps-left steps
                steps []]
           (if (empty? steps-left)
             steps
             (let [work-on (->> steps-left
                                (filter #(empty? (get data % [])))
                                (sort)
                                (first))]
               (recur
                 (into {} (map (fn [[k v]] [k (filter #(not= work-on %) v)]) data))
                 (disj steps-left work-on)
                 (conj steps work-on)))))
         (str/join ""))))

(defn part-2 [input]
  (let [data (parse-input input)
        steps (all-steps input)]
    (loop [count 0
           steps-left steps]
      (if (empty? steps-left)
        0
        (recur
          (inc count)
          ())))))

(defn run [input]
  [(part-1 input) (part-2 input)])
