(ns hello.app.page.reagent
  (:require [reagent.core :as reagent :refer [cursor atom]]
            [com.firstlinq.ssr.router :refer [navigate-to path-for]]
            [com.firstlinq.ssr.log :as log :include-macros true]
            [com.firstlinq.ssr.view.reagent :refer []]))

(defn greeting-page [data opts]
  (let [greeting-cursor (cursor data [:greeting])]
    (fn [data {:keys [router]}]
      [:div
       [:span @greeting-cursor]
       [:a {:href "/"} "Click here to go back home"]])))

(defn home-page [data opts]
  (let [username (atom "")]
    (fn [data {:keys [router]}]
      [:form {:on-submit
              (fn [e]
                (let [uname @username]
                  (log/info "name = " uname, "navigating to "
                            (path-for router :hello {:username uname}))
                  (navigate-to router [:hello {:username uname}])
                  (.preventDefault e)))}
       [:label "Enter your name: "]
       [:input {:placeholder "(name)"
                :value       @username
                :on-change   (fn [e]
                               (reset! username (-> e .-target .-value)))}]
       [:button {:type "submit"} "Submit"]])))

(def pages {:home home-page :hello greeting-page})

(defn main-app [opts]
  (fn [state]
    (let [route-id (get-in @state [:route :id])
          page     (get pages route-id home-page)]
      [page state opts])))