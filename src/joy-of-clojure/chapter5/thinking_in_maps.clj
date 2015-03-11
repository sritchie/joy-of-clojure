;; Thinking in maps
;; ---------------------------------------------------------------------------------------------------------------------
;; It's difficult to write a program of any significant size without the need for a map of some sort. The use of maps is
;; ubiquitous in writing software because, frankly, it's hard to imagine a more robust data structure. But we as
;; programmers tend to view maps as a special case structure outside the normal realm of data objects and classes.
;; The object-oriented school of thought has relegated the map to being a supporting player in favor of the class. We're
;; not going to talk about the merits (or lack thereof) for this relegation here, but in upcoming sections we'll discuss
;; moving away from thinking in classes and instead thinking in the sequence abstraction, maps, protocols, and types.
;; Having said all that, it need hardly be mentioned that maps should be used to store named values. In this section,
;; we'll talk about the different types of maps and the trade-offs surrounding each.

;; Hash maps
;; ---------
;; Arguably, the most ubiquitous form of map found in Clojure programs is the hash map, which provides an unsorted
;; key/value associative structure. In addition to the literal syntax touched on earlier, hash maps can be created using
;; the hash-map function, which likewise takes alternating key/value pairs, with or without commas:
(hash-map :a 1, :b 2, :c 3, :d 4, :e 5)
;;=> {:e 5, :c 3, :b 2, :d 4, :a 1}

;; Clojure hash maps support heterogeneous keys, meaning they can be of any type and each key can be of a differing
;; type, as this code shows:
(let [m {:a 1, 1 :b, [1 2 3] "4 5 6"}]
  [(get m :a) (get m [1 2 3])])
;;=> [1 "4 5 6"]

;; As we mentioned before, many of Clojure's collection types can be used as functions, and in the case of maps they're
;; functions of their keys. Using maps this way acts the same as the use of the get function in the previous code
;; example, as shown when building a vector of two elements:
(let [m {:a 1, 1 :b, [1 2 3] "4 5 6"}]
  [(m :a) (m [1 2 3])])
;;=> [1 "4 5 6"]

;; Providing a map to the seq function returns a sequence of map entries:
(seq {:a 1, :b 2})
;;=> ([:b 2] [:a 1])

;; This sequence appears to be composed of the sets of key/value pairs contained in vectors, and for all practical
;; purposes it should be treated as such. In fact, you can create a new hash map using this precise structure:
(into {} [[:a 1] [:b 2]])
;;=> {:a 1, :b 2}

;; Even if your embedded pairs aren't vectors, they can be made to be for building a new map:
(into {} (map vec '[(:a 1) (:b 2)]))
;;=> {:a 1, :b 2}

;; Your pairs don't have to be explicitly grouped, because you can use apply to create a hash map given that the
;; key/value pairs are laid out in a sequence consecutively:
(apply hash-map [:a 1 :b 2])
;;=> {:b 2, :a 1}

;; You can also use apply this way with sorted-map and array-map. Another fun way to build a map is to use zipmap to
;; "zip" together two sequences, the first of which contains the desired keys and the second their corresponding values:
(zipmap [:a :b] [1 2])
;;=> {:b 2, :a 1}

;; The use of zipmap illustrates nicely the final property of map collections. Hash maps in Clojure have no order
;; guarantees. If you do require ordering, then you should use sorted maps, discussed next.



