(ns advent-of-code.2018.day-5
  (:require [clojure.string :as str]
            [clojure.set :refer [difference]]
            [advent-of-code.util :as util]))

(def test-input "dabAcCaCBAcCcaDA")
(def letters (str/split "abcdefghijklmnopqrstuvwxyz" #""))

(defn react? [first second]
  (and first second
       (not (= first second))
       (= (str/upper-case first) (str/upper-case second))))

(defn process-reactions [input]
  (reduce (fn [coll char]
            (if (react? char (peek coll))
              (pop coll)
              (conj coll char)))
          (vec [])
          input))

(defn letter-pattern [letter]
  (re-pattern (str "[^" (str/lower-case letter) (str/upper-case letter) "]")))

(defn part-1 [input]
  (let [input (-> (str/trim input)
                  (str/split #""))]
    (->> input
         (process-reactions)
         (count))))

(defn part-2 [input]
  (let [input (str/trim input)]
    (->> letters
         (map #(re-seq (letter-pattern %) input))
         (map process-reactions)
         (sort-by count)
         (first)
         (count))))


(defn run [input]
  [(part-1 input) (part-2 input)])
