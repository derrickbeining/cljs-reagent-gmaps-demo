(ns app.components.origin-destination-form
  (:require [reagent.core :as r]
            ["@blueprintjs/core" :refer [Button
                                         Card
                                         ControlGroup
                                         FormGroup
                                         InputGroup
                                         Intent]]))

(defn OriginDestinationForm []
  (let [form-state (r/atom {:origin ""
                            :destination ""
                            :loading false})
        handleOriginChange! #(swap! form-state assoc :origin (->> % .-target.value))
        handleDestinationChange! #(swap! form-state assoc :destination (->> % .-target.value))]

    (fn [{handle-submit! :on-submit}]
      (let [{:keys [destination loading origin]} @form-state
            handle-submit!' (fn []
                              (swap! form-state #(-> %
                                                     (assoc :origin "")
                                                     (assoc :destination "")
                                                     (assoc :loading true)))
                              (-> (handle-submit! origin destination)
                                  (.then #(swap! form-state assoc :loading false))
                                  (.catch #(swap! form-state assoc :loading false))))]
        [:> FormGroup
         [:> ControlGroup {:fill false
                           :vertical true}
          [:> InputGroup {:large true
                          :left-icon "locate"
                          :on-change handleOriginChange!
                          :placeholder "Origin ( e.g. Atlanta, GA )"
                          :type "text"
                          :value origin}]
          [:> InputGroup {:large true
                          :left-icon "map-marker"
                          :on-change handleDestinationChange!
                          :placeholder "Destination ( e.g. Portland, OR )"
                          :type "text"
                          :value destination}]
          [:> Button {:intent (.-PRIMARY Intent)
                      :large true
                      :loading loading
                      :on-click handle-submit!'}
           "Let's do this!"]]]))))

