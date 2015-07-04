(ns hello.app.state
  (:require [reagent.core :refer [atom]]))

(defonce app-state (atom {}))
