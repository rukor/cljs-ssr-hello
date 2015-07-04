(ns hello.app.main
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [om.core :as om :include-macros true]
            [reagent.core :as reagent]
            [cljs.core.async :refer [<!]]
            [hello.app.state :refer [app-state]]
            [hello.app.page.om :as om-page]
            [hello.app.page.reagent :as reagent-page]
            [hello.app.routes :refer [app-routes]]
            [hello.app.util :refer [by-id]]
            [com.firstlinq.ssr :refer [redirect-key]]
            [com.firstlinq.ssr.api :refer [create-service]]
            [com.firstlinq.ssr.log :as log :include-macros true]
            [com.firstlinq.ssr.state :refer [get-state hydrate transit-serialiser]]
            [com.firstlinq.ssr.routes :refer [create-route-handler]]
            [com.firstlinq.ssr.router.silk :refer [silk-router]]
            ))

(defn render-om-home [state service router]
  (let [opts {:service service :router router}]
    (om/root om-page/main-app state
             {:target (by-id "app")
              :opts   opts})))

(defn render-reagent-home [state service router]
  (let [opts      {:service service :router router}
        component (reagent-page/main-app opts)]
    (reagent/render [component state] (by-id "app"))))

(defn ^{:export true} start
  "Entry point for application"
  []
  (enable-console-print!)
  (log/info "Starting application")
  (let [handler    (create-route-handler app-state)
        service    (create-service app-state)
        router     (silk-router app-routes handler)
        serialiser (transit-serialiser)]
    (hydrate serialiser app-state (by-id "app-state"))
    (render-reagent-home app-state service router)))
