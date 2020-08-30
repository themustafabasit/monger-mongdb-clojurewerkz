(ns mongodb.advanced-querying
  (:refer-clojure :exclude [sort find])
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [monger.conversion :refer [from-db-object]]
            [monger.query :refer [with-collection find fields]])
  (:import [org.bson.types ObjectId]))

(def conn (mg/connect))
(def db-name "mydb")
(def db (mg/get-db conn db-name))
(def coll "students")


(defn initial-setup
  [db coll]
  (mc/remove db coll)
  (mc/insert db coll {:_id (ObjectId.)
                        :name "Mehroosha Jalal"
                        :gender "f"
                        :email "mehr@gmail.com"})

  (mc/insert db coll {:_id (ObjectId.)
                        :name "Nuzhat-ul-Firdous"
                        :gender "f"
                        :email "nuzhat@gmail.com"})
  "Done!")

(initial-setup db coll)
;; => "Done!"



(mc/find-maps db coll {} [:name])
;; => ({:_id #object[org.bson.types.ObjectId 0x5ed007db "5f4bc58f799d906fafc7db03"], :name "Mehroosha Jalal"}
;;     {:_id #object[org.bson.types.ObjectId 0x719f926c "5f4bc58f799d906fafc7db04"], :name "Nuzhat-ul-Firdous"})

(mc/find db coll {:name "Mehroosha Jalal"})
;; => #object[com.mongodb.DBCursor 0x24719ce1 "DBCursor{collection=DBCollection{database=DB{name='mydb'}, name='students'}, find=com.mongodb.client.model.DBCollectionFindOptions@62210578}"]

(from-db-object (mc/find db coll {:name "Mehroosha Jalal"}) true)
;; => #object[com.mongodb.DBCursor 0x3279f8a8 "DBCursor{collection=DBCollection{database=DB{name='mydb'}, name='students'}, find=com.mongodb.client.model.DBCollectionFindOptions@4a3baf90}"]

(seq (from-db-object (mc/find db coll {:name "Mehroosha Jalal"}) true))
;; => ({"_id" #object[org.bson.types.ObjectId 0x9715fe0 "5f4bc58f799d906fafc7db03"], "name" "Mehroosha Jalal", "gender" "f", "email" "mehr@gmail.com"})

(seq (from-db-object (mc/find db coll) true))
;; => ({"_id" #object[org.bson.types.ObjectId 0x3a04efb8 "5f4bc58f799d906fafc7db03"], "name" "Mehroosha Jalal", "gender" "f", "email" "mehr@gmail.com"}
;;     {"_id" #object[org.bson.types.ObjectId 0x3f32f1d8 "5f4bc58f799d906fafc7db04"], "name" "Nuzhat-ul-Firdous", "gender" "f", "email" "nuzhat@gmail.com"})
