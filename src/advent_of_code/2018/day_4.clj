(ns advent-of-code.2018.day-4
  (:require [clojure.string :as str]
            [clojure.set :refer [difference]]
            [advent-of-code.util :as util]))

(def test-input "[1518-11-01 00:00] Guard #10 begins shift\n[1518-11-01 00:05] falls asleep\n[1518-11-01 00:25] wakes up\n[1518-11-01 00:30] falls asleep\n[1518-11-01 00:55] wakes up\n[1518-11-01 23:58] Guard #99 begins shift\n[1518-11-02 00:40] falls asleep\n[1518-11-02 00:50] wakes up\n[1518-11-03 00:05] Guard #10 begins shift\n[1518-11-03 00:24] falls asleep\n[1518-11-03 00:29] wakes up\n[1518-11-04 00:02] Guard #99 begins shift\n[1518-11-04 00:36] falls asleep\n[1518-11-04 00:46] wakes up\n[1518-11-05 00:03] Guard #99 begins shift\n[1518-11-05 00:45] falls asleep\n[1518-11-05 00:55] wakes up")

(defn entry-information [entry]
  (rest (re-find #"(\[.*\]) (?:Guard #(\d+))?" entry)))

(defn is-guard-entry [entry]
  (str/includes? entry "Guard"))

(defn is-sleep-entry [entry]
  (not (is-guard-entry entry)))

(defn is-awake [entry]
  (str/includes? entry "wakes up"))

(defn is-asleep [entry]
  (not (is-awake entry)))

(defn get-minutes [time]
  (->> (re-find #"\d+:(\d+)" time)
       (last)
       (util/parse-int)))

(defn minute-counts [minutes]
  (->> minutes
       (map #(apply range (reverse %)))
       (flatten)
       (frequencies)
       (sort-by val)))

(defn parse-entry [entry]
  (let [[time guard] (entry-information entry)]
    (cond
      (is-guard-entry entry) {:type  :shift-start
                              :guard guard
                              :time time
                              :minutes  (get-minutes time)}
      (is-sleep-entry entry) {:type  (if (is-awake entry) :awake :asleep)
                              :guard guard
                              :time time
                              :minutes  (get-minutes time)})))

(defn nearest-guard [entries]
  (->> (reverse entries)
       (filter #(is-guard-entry %))
       (first)
       (parse-entry)
       (:guard)))

(defn extract-times [[id guard-data]]
  (let [grouped-minutes (->> guard-data
                             (filter #(or (= (:type %) :awake)
                                          (= (:type %) :asleep)))
                             (map :minutes)
                             (partition 2))]
    {:id id
     :minutes grouped-minutes
     :total (->> grouped-minutes
                 (reduce (fn [sum [end start]]
                           (+ sum (- end start)))
                         0))}))

(defn guard-data [input]
  (let [entries (->> input
                     (str/split-lines)
                     (sort))]
    (->> (loop [entries entries
                entries-seen []
                parsed-data {}]
           (if (empty? entries)
             parsed-data
             (let [entry (first entries)
                   entries-seen (conj entries-seen entry)
                   guard (nearest-guard entries-seen)]
               (recur (rest entries)
                      entries-seen
                      (update parsed-data guard conj (parse-entry entry)))))))))

(defn part-1 [input]
  (let [guard (->> (guard-data input)
                   (map extract-times)
                   (apply max-key :total))]
    (->> guard
         (:minutes)
         (minute-counts)
         (last)
         (first)
         (* (util/parse-int (:id guard))))))

(defn part-2 [input]
  0)

(defn run [input]
  [(part-1 input) (part-2 input)])
