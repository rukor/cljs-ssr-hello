(ns hello.app.service
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [com.firstlinq.ssr.api :refer [handle-request]]
            [cljs.core.async :refer [chan <! >! put!]]
            [clojure.string :as str]))


;------------ api calls ---------------
; Make your service api calls and return a core.async channel, with result map containing the :body key.
; This is already the case for cljs-http responses.

(defmethod handle-request :say-hello [state _ username]
  (let [ch (chan 1)]
    (js/setTimeout
      (fn []
        (put! ch {:body (str "Hello " username " from cljs-ssr-node :) ")}))
      16)
    ch))
