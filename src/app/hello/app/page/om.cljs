(ns hello.app.page.om
  (:require [om.dom :as dom :include-macros true]
            [om.core :as om :include-macros true]
            [com.firstlinq.ssr.router :refer [navigate-to path-for]]
            [com.firstlinq.ssr.log :as log :include-macros true]
            [com.firstlinq.ssr.view.om :refer [link]]))

(defn greeting-page [data owner {:keys [router]}]
  (reify
    om/IRender
    (render [_]
      (dom/div #js{}
               (dom/span nil (:greeting data))
               (link router {:href "/"}
                     "Click here to go back home")))))

(defn home-page [data owner {:keys [router]}]
  (reify
    om/IInitState
    (init-state [_] {:username ""})
    om/IRenderState
    (render-state [_ {:keys [username]}]
      (dom/form #js{:onSubmit (fn [e]
                                (log/info "name = " username, "navigating to "
                                          (path-for router :hello {:username username}))
                                (navigate-to router [:hello {:username username}])
                                (.preventDefault e))}
                (dom/label nil "Enter your name: ")
                (dom/input #js{:placeholder "(name)"
                               :value       (om/get-state owner :username)
                               :onChange    (fn [e]
                                              (let [username (-> e .-target .-value)]
                                                (om/set-state! owner :username username)))})
                (dom/button #js{:type "submit"} "Submit")))))

(def pages {:home home-page :hello greeting-page})

(defn main-app [data owner opts]
  (reify
    om/IRender
    (render [_]
      (let [route-id (get-in data [:route :id])
            page     (get pages route-id home-page)]
        (om/build page data {:opts opts})))))