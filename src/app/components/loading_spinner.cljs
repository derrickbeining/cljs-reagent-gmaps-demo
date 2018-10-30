(ns app.components.loading-spinner
  (:require ["@blueprintjs/core" :refer [Spinner]]))

(def styles {:map-loading-spinner {:display "flex"
                                   :justify-content "center"
                                   :align-content "center"
                                   :height "100%"
                                   :width "100%"}})

(defn LoadingSpinner [] [:div {:style (:map-loading-spinner styles)}
        [:> Spinner {:size (.-LARGE Spinner)}]])