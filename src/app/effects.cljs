(ns app.effects
  (:require ["ramda" :as R]
            ["@blueprintjs/core" :refer [Intent Position Toaster]]
            [app.state :refer [state]]
            [app.api :as api]))

(def AppToaster (.create Toaster #js{:position (R/prop "TOP" Position)
                                     :style #js{:zIndex 10}}))

(defn show-toast [toast-props]
   (.show AppToaster toast-props))

(defn show-no-results-toast []
  (show-toast #js{:message "No routes found"
                  :intent (R/prop "WARNING" Intent)}))

(defn show-something-went-wrong-toast []
  (show-toast #js{:message "Something went wrong"
                  :intent (R/prop "WARNING" Intent)}))

(defn load-gmap-key! []
  (-> (api/fetch-secrets!)
      (.then (R/applySpec #js{:gmaps-key (R/prop "gmaps_key")}))
      (.then #(js->clj % :keywordize-keys true))
      (.then #(swap! state (constantly (into @state %))))))

(defn load-map-data! [origin destination]
  (-> (api/fetch-directions! origin destination)
      (.then #(js->clj % :keywordize-keys true))
      (.then #(swap! state (constantly (into @state %))))
      (.catch (R/cond #js[#js[(R/pathEq #js["response" "status"] 404) show-no-results-toast]
                          #js[(R/always true) show-something-went-wrong-toast]]))))