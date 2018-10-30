(ns app.components.search-panel
  (:require ["@blueprintjs/core" :refer [Card Colors Elevation H2 HTMLTable]]
            [app.components.origin-destination-form :refer [OriginDestinationForm]]))

(def styles
  {:app-card-container {:display "flex"
                        :position "absolute"
                        :height "100%"
                        :top 0
                        :left 0
                        :width "100%"}
   :app-card {:backgroundColor (.-LIGHT_GRAY5 Colors)
              :display "flex"
              :flexDirection "column"
              :maxWidth "400px"
              :minWidth "300px"
              :width "40%"
              :zIndex 1}
   :directions {:height "100%"
                :flex "1 2 auto"
                :marginTop 10
                :overflow "scroll"
                :width "100%"}})

(defn SearchPanel [props]
  (let [{:keys [steps
                on-submit]} props]

    [:div {:style (:app-card-container styles)}
     [:> Card {:style (:app-card styles)
               :elevation (.-FOUR Elevation)}
      [:> H2 {:style {:text-align "center"}}
       "Where to?"]

      [OriginDestinationForm {:on-submit on-submit}]

      (when (-> steps empty? not)
        [:div {:style (:directions styles)}
         [:> HTMLTable {:condensed false
                        :bordered true
                        :interactive true}
          [:thead
           [:tr
            [:th "Directions"]
            [:th "Dist."]]]

          [:tbody
           (for [step steps]
             [:tr {:key (-> step :start_location str)}
              [:td {:dangerouslySetInnerHTML {:__html (-> step :html_instructions)}}]
              [:td (-> step (get-in [:distance :text]))]])]]])]]))