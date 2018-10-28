(ns dd-challenge.app
  (:require [reagent.core :as r]
            ["@blueprintjs/core" :refer [Card Colors Elevation H2]]))

(def styles
  {:root-container {:display "flex"
                    :flex-direction "column"
                    :height "100%"
                    :justify-content "space-around"}
   :app-card {:backgroundColor (.-LIGHT_GRAY5 Colors)
              :justify-content "space-evenly"
              :margin "0 auto"
              :max-width "500px"
              :min-width "300px"
              :width "50%"}})


(defn App []
  [:div {:style (:root-container styles)}
   [:> Card {:style (:app-card styles)
             :elevation (.-FOUR Elevation)}
    [:> H2 {:style {:text-align "center"}} "Where to?"]
    ]])



(defn init! []
  (r/render App (.getElementById js/document "root")))