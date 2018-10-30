(ns app.app-root
  (:require [reagent.core :as r]
            [app.components.loading-spinner :refer [LoadingSpinner]]
            [app.components.google-map :refer [GMaps]]
            [app.components.search-panel :refer [SearchPanel]]
            [app.effects :refer [load-map-data!
                                 load-gmap-key!]]))

(def styles
  {:root-container {:height "100%"}
   :map {:border 0
         :align-self "flex-end"
         :height "100vh"
         :width "100%"
         :zIndex 0}})

(defn AppRoot [state]
  (r/create-class
   {:component-did-mount (fn [] (load-gmap-key!))

    :reagent-render
    (fn []
      (let [{:keys [gmaps-key
                    bounds
                    steps
                    originCoords
                    destinationCoords
                    polylinePath]} @state]
        (if (nil? gmaps-key)
          [LoadingSpinner]

          [:div {:style (:root-container styles)}
           [SearchPanel {:steps steps
                         :on-submit load-map-data!}]

           [:div {:style (:map styles)}
            [:> GMaps {:apiKey gmaps-key
                       :bounds bounds
                       :originCoords originCoords
                       :destinationCoords destinationCoords
                       :polylinePath polylinePath}]]])))}))

