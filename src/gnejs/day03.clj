(ns gnejs.day03
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn str->arr [s]
  (mapv (fn [c] (Byte/parseByte (str c))) s))

(def test-input
  (list (str->arr "00100")
        (str->arr "11110")
        (str->arr "10110")
        (str->arr "10111")
        (str->arr "10101")
        (str->arr "01111")
        (str->arr "00111")
        (str->arr "11100")
        (str->arr "10000")
        (str->arr "11001")
        (str->arr "00010")
        (str->arr "01010")))


(defn read-input []
  (->> (str/split-lines (slurp (io/resource "day03.txt")))
       (map str->arr)))

(defn gamma-rate [bins]
  (apply map
         (fn [& xs]
           (let [frq (frequencies xs)]
             (if (>= (get frq 1) (get frq 0))
               1 0)))
         bins))

(defn inverse-str [bin]
   (mapv (fn [c] (case c
                  0 1
                  1 0))
        bin))

(defn arr->dec [bin]
  (Integer/parseInt (str/join bin) 2))

(defn power-consumption [report]
  (let [gamma-rate (gamma-rate report)
        epsilon-rate (inverse-str gamma-rate)]
    (* (arr->dec gamma-rate) (arr->dec epsilon-rate))))

(defn most-common-bit [bits]
  (if (>= (reduce + 0 bits)
          (/ (count bits) 2))
    1 0))

(defn bits-at-pos [bins pos] (mapv #(get (vec %) pos) bins))

(defn oxygen-generator-rating [bins]
  (->> (loop [idx  0
              bins bins]
         (if (= (count bins) 1)
           (first bins)
           (let [bit (most-common-bit (bits-at-pos bins idx))]
             (recur (inc idx)
                    (filter #(= bit (get % idx)) bins)))))
       (arr->dec)))

(defn co2-scrubber-rating [bins]
  (->> (loop [idx  0
              bins bins]
         (if (= (count bins) 1)
           (first bins)
           (let [bit (most-common-bit (bits-at-pos bins idx))
                 bit (if (= bit 1) 0 1)]
             (recur (inc idx)
                    (filter #(= bit (get % idx)) bins)))))
       (arr->dec)))

(defn solve-2 []
  (let [bins (read-input)
        ox (oxygen-generator-rating bins)
        co2 (co2-scrubber-rating bins)]
    (* ox co2)))
