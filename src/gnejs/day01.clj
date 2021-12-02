(ns gnejs.day01 "First day is up!"
    (:require [clojure.java.io :as io]))

(defn read-input [res]
  (with-open [rdr (io/reader (io/resource res))]
    (->> (line-seq rdr)
         (map #(Integer/parseInt %))
         (doall))))

(defn summed-windows [ns]
  (->> ns
       (partition 3 1 ns)
       (map #(apply + %))))

(defn count-depth-inc [ns]
  (->> ns
       (partition 2 1)
       (filter (fn [[a b]] (< a b)))
       (count)))

(defn solve-1 []
  (-> (read-input "day01_1.txt")
      (count-depth-inc)))

(defn solve-2 []
  (-> (read-input "day01_1.txt")
      (summed-windows)
      (count-depth-inc)))

(defn short-one []
  (with-open [rdr (-> (io/resource "day01_1.txt")
                      (io/reader))]
    (let [ns (->> (line-seq rdr)
                  (map #(Integer/parseInt %)))]
      (println "Increasing depths:"
               (->> ns
                    (partition 2 1)
                    (filter #(apply < %))
                    (count)))
      (println "Increasing sliding window depths:"
               (->> ns
                    (partition 3 1)
                    (map #(apply + %))
                    (partition 2 1)
                    (filter #(apply < %))
                    (count))))))
