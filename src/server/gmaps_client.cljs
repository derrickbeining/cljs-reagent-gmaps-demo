(ns server.gmaps-client
  (:require ["@google/maps" :refer [createClient]]))

(def ^:private client (createClient #js {:key js/process.env.GMAPS_API_KEY
                                         :Promise js/Promise}))

(defn getDirections [origin destination]
  (-> client
      (.directions #js{:origin origin
                       :destination destination
                       :mode "driving"})
      (.asPromise)
      (.catch js/console.error)))