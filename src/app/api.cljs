(ns app.api
  (:require ["ramda" :as R]
            ["axios" :as axios]))

(def ^:private client (.create axios #js{:baseURL "http://localhost:3000"}))

(defn fetch-secrets! []
  (-> client
      (.get "/secrets")
      (.then (R/prop "data"))))

(defn fetch-directions! [origin destination]
  (-> client
      (.post (str "/directions")
             #js{:origin origin
                 :destination destination})
      (.then (R/prop "data"))))