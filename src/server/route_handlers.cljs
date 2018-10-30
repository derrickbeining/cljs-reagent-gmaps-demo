(ns server.route-handlers
  (:require ["ramda" :as R]
            ["google-polyline" :as poly]
            [server.gmaps-client :as gmaps]
            ["path" :as path]))

(defn handle-get-secrets [req res]
  (.send res #js{:gmaps_key js/process.env.GMAPS_API_KEY}))

(defn handle-get-index-html [req res]
  (.sendFile res (path/join js/__dirname ".." "app" "index.html")))

(defn handle-post-directions [req res]
  (let [origin (R/path #js["body" "origin"] req)
        destination (R/path #js["body" "destination"] req)]
    (-> (gmaps/getDirections origin destination)
        (.then (R/pipe
                (R/path #js["json" "routes" 0])
                (R/applySpec #js{:destinationCoords (R/path #js["legs" 0 "end_location"])
                                 :originCoords (R/path #js["legs" 0 "start_location"])
                                 :steps (R/path #js["legs" 0 "steps"])
                                 :polylinePath (R/path #js["legs" 0 "steps"])
                                 :bounds (R/applySpec #js{:north (R/path #js["bounds" "northeast" "lat"])
                                                          :east (R/path #js["bounds" "northeast" "lng"])
                                                          :south (R/path #js["bounds" "southwest" "lat"])
                                                          :west (R/path #js["bounds" "southwest" "lng"])})})
                (R/evolve #js{:polylinePath (R/chain (R/pipe (R/path #js["polyline" "points"])
                                                             poly/decode
                                                             (R/chain (R/zipObj #js["lat" "lng"]))))})))

        (.then #(.json res %))
        (.catch js/console.error))))