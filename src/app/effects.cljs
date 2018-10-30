(ns app.effects
  (:require [app.state :refer [state]]
            [app.api :as api]
            ["ramda" :as R]))

(defn load-gmap-key! []
  (-> (api/fetch-secrets!)
      (.then (R/applySpec #js{:gmaps-key (R/prop "gmaps_key")}))
      (.then #(js->clj % :keywordize-keys true))
      (.then #(swap! state (constantly (into @state %))))))

(defn load-map-data! [origin destination]
  (-> (api/fetch-directions! origin destination)
      (.then #(js->clj % :keywordize-keys true))
      (.then #(swap! state (constantly (into @state %))))))