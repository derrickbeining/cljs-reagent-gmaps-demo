(ns server.main
  (:require ["path" :as path]
            ["express" :as express]
            ["morgan" :as logger]
            [server.route-handlers :refer [handle-get-secrets
                                           handle-get-index-html
                                           handle-post-directions]]))

; a place to hang onto the server so we can stop/start it
(defonce server-ref
  (volatile! nil))

(defn init! []
  (js/console.log ">>>> starting server")
  (-> (express)
      ;; ----------- MIDDLEWARE -----------
      (.use (logger "dev"))
      (.use (express/json))
      (.use (express/static (path/join js/__dirname ".." "app")))
      ;; ----------- ROUTES --------------
      (.get "/" handle-get-index-html)
      (.get "/secrets" handle-get-secrets)
      (.post "/directions" handle-post-directions)
      ;; ---------------------------------
      (.listen 3000 (fn [err]
                      (if err
                        (js/console.error ">>>> server start failed")
                        (js/console.info ">>>> http server running at http://localhost:3000"))))
      (#(vreset! server-ref %))))


;; ------------------- DEV BUILD HOOKS -----------------------
(defn start
  "Hook to start. Also used as a hook for hot code reload."
  []
  (js/console.warn "start called")
  (init!))

(defn stop
  "Hot code reload hook to shut down resources so hot code reload can work"
  [done]
  (js/console.warn "stop called")
  (when-some [srv @server-ref]
    (.close srv
            (fn [err]
              (js/console.log "stop completed" err)
              (done)))))
