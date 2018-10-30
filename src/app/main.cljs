(ns app.main
  (:require [reagent.core :as r]
            [app.app-root :refer [AppRoot]]
            [app.state :refer [state]]))

(defn init! []
  (r/render [AppRoot state] (.getElementById js/document "root")))