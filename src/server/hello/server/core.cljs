(ns hello.server.core
  (:require [cljs.nodejs :as node]
            [com.firstlinq.ssr.server :as server]
            [com.firstlinq.ssr.router.silk :refer [create-request->state silk-router]]
            [com.firstlinq.ssr.view.om :refer [make-om-renderer]]
            [com.firstlinq.ssr.view.reagent :refer [make-reagent-renderer]]
            [hello.app.page.om :as om-page]
            [hello.app.page.reagent :as reagent-page]
            [hello.app.routes :refer [app-routes]]))

(node/enable-util-print!)

(defn -main []
  (let [env-docroot      (-> node/process .-env .-DOCROOT)  ; read docroot from env
        env-port         (-> node/process .-env .-PORT)
        docroot          (if (nil? env-docroot) "resources/public" env-docroot)
        port             (if (nil? env-port) "3000" env-port)
        router           (silk-router app-routes)
        om-renderer      (make-om-renderer om-page/main-app router)
        reagent-renderer (make-reagent-renderer reagent-page/main-app router)]
    (server/start :docroot docroot
                  :port port
                  :template-file "resources/index.html"
                  :renderer reagent-renderer
                  :request->state (create-request->state app-routes))))

(set! *main-cli-fn* -main)
