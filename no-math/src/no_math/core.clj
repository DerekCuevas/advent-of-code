(ns no-math.core
  (:require [clojure.string :as str])
  (:gen-class))

(def input (str/split (slurp "resources/input.txt") #"\n"))

(defn str->dims [dim-str]
  (->> (str/split dim-str #"x")
       (map read-string)
       (zipmap [:l :w :h])))

(defn areas [{:keys [l w h]}]
  (list (* l w) (* w h) (* h l)))

(defn surface-area [dims]
  (->> (areas dims)
       (map #(* 2 %))
       (reduce +)))

(defn slack [dims]
  (apply min (areas dims)))

(defn required-wrapping-paper [dims]
  (+ (surface-area dims) (slack dims)))

(defn total-required-wrapping-paper [dims-list]
  (->> dims-list
       (map (comp required-wrapping-paper str->dims))
       (reduce +)))

(defn smallest-perimeter [dims]
  (->> (vals dims)
       sort
       (take 2)
       (map #(+ % %))
       (apply +)))

(defn ribbon-for-bow [dims]
  (reduce * (vals dims)))

(defn required-ribbon [dims]
  (+ (smallest-perimeter dims) (ribbon-for-bow dims)))

(defn total-required-ribbon [dims-list]
  (->> dims-list
       (map (comp required-ribbon str->dims))
       (reduce +)))
