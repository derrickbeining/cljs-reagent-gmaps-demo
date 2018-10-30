(ns app.components.google-map
  (:require [reagent.core :as r]
            ["ramda" :as R]
            ["google-maps-react" :refer [GoogleApiWrapper Map Marker Polyline]]
            [app.components.loading-spinner :refer [LoadingSpinner]]))

(def with-gmaps
  (GoogleApiWrapper
   (fn [p]
     #js{:apiKey (.-apiKey p)
         :libraries #js["drawing"]
         :LoadingContainer (r/reactify-component LoadingSpinner)})))

(def GMaps
  (with-gmaps
    (fn [p]
      (r/as-element
       [:> Map {:bounds (R/prop "bounds" p)
                :initialCenter {:lat 40.7128, :lng -74.0060}
                :google (R/prop "google" p)}
        [:> Marker {:position (R/prop "originCoords" p)}]
        [:> Marker {:position (R/prop "destinationCoords" p)}]
        (when #(->> p (R/prop "polylinePath") (R/isEmpty) (R/not))
          [:> Polyline {:path (R/prop "polylinePath" p)}])]))))