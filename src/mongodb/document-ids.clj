(ns mongodb.document-ids
  (:require [monger.core :as mg]
            [monger.collection :as mc])
  (:import org.bson.types.ObjectId))

;; if you need a generated object id. You do so by instantiating org.bson.types.ObjectId without arguments

(ObjectId.)
;; => #object[org.bson.types.ObjectId 0x132b6e99 "5f4001d4799d900ab9cde975"]

(ObjectId. "4fea999c0364d8e880c05157")
;; => #object[org.bson.types.ObjectId 0x3d0fb4e1 "4fea999c0364d8e880c05157"]

(ObjectId. "123")
;; => Execution error (IllegalArgumentException) at org.bson.types.ObjectId/parseHexString (ObjectId.java:550).
;; => invalid hexadecimal representation of an ObjectId: [123]

(let [conn (mg/connect)
      db-name "mydb"
      db (mg/get-db conn db-name)
      coll-name "students"
      oid (ObjectId.)
      doc {:name "Mudasir Mir" :msg "success!"}]
  (mc/insert db coll-name (merge doc
                            {:_id oid})))
;; => #object[com.mongodb.WriteResult 0x497e765d "WriteResult{n=0, updateOfExisting=false, upsertedId=null}"]
