(ns gnejs.day02
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def test-instr
  (list
   [:forward 5]
   [:down 5]
   [:forward 8]
   [:up 3]
   [:down 8]
   [:forward 2]))

(defn read-instructions [resource]
  (with-open [rdr (-> (io/resource resource)
                      (io/reader))]
    (doall
     (keep
      (fn [l]
        (when-let [[_ dir val] (re-find #"(\w+)\s+(\d+)" l)]
          [(keyword dir) (Integer/parseInt val)]))
      (line-seq rdr)))))

(defn run-submarine-1 [instructions]
  (reduce (fn [[h d :as pos] [dir val]]
            (case dir
              :forward (map + pos [val 0])
              :up (map - pos [0 val])
              :down (map + pos [0 val])))
          [0 0] instructions))

(defn run-submarine-2 [instructions]
  (reduce (fn [[aim h d :as pos] [dir val]]

            (case dir
              :forward (map + pos [0 val (* aim val)])
              :up      (map - pos [val 0 0])
              :down    (map + pos [val 0 0])))

          [0 0 0] instructions))

(defn solve-1 []
  (->> (read-instructions "day02_1.txt")
       (run-submarine-1)
       (apply *)))

(defn solve-2 []
  (let [[_ h d] (->> (read-instructions "day02_1.txt")
                     (run-submarine-2))]
    (* h d)))
