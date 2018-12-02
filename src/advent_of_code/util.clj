(ns advent-of-code.util
  (:require [clj-http.client :as client]
            [clojure.java.io :as io]))

(def session-cookie ["session" {:value (slurp (io/resource "session"))}])

(defn parse-int [n]
  (Integer/parseInt n))

(defn get-input [year day]
  (->> (client/get (str "https://adventofcode.com/" year "/day/" day "/input") {:cookies [session-cookie]})
       (:body)))