(ns advent-of-code.2018.day-1)

(defn part-1 [input]
  (reduce + input))

(defn part-2 [input]
  (loop [curr 0
         seen #{}
         list (cycle input)]
    (if (contains? seen curr)
      curr
      (recur
        (+ curr (first list)) (conj seen curr) (rest list)))))

(defn run [input]
  [(part-1 input) (part-2 input)])