(ns gnejs.day01-test
  (:require [gnejs.day01 :as sut]
            [clojure.test :refer :all]))


(deftest test-step-1
  (testing "Increasing depth"
    (is (= 7
           (sut/count-depth-inc [199
                                 200
                                 208
                                 210
                                 200
                                 207
                                 240
                                 269
                                 260
                                 263])))))
