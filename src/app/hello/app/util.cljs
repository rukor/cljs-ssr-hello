(ns hello.app.util)

(defn by-id [id]
  (.getElementById js/document id))
