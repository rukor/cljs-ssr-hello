(ns hello.app.routes
  (:require [com.firstlinq.ssr]
            [com.firstlinq.ssr.state :refer [get-state]]
            [com.firstlinq.ssr.routes :include-macros true :refer-macros [defhandler]]
            [hello.app.service]))

(def app-routes
  [[:home [[]]]                                             ; home page
   [:hello [["greeting" :username]]]                        ; greeting page
   ])

(defhandler :home "Home Page" [])

(defhandler :hello "Greeting Page" [username]
            :greeting [:say-hello username])

