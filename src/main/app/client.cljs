(ns app.client
  (:require
   [app.ui.root :as root]
   [com.fulcrologic.fulcro.application :as app]
   [fulcro.inspect.tool :as it]
   [taoensso.timbre :as log]))

(defonce SPA (app/fulcro-app {}))

(defn ^:export refresh []
  (log/info "Hot code Remount")
  (app/mount! SPA root/Root "app"))

(defn ^:export init []
  (log/info "Application starting.")
  (app/set-root! SPA root/Root {:initialize-state? true})
  (it/add-fulcro-inspect! SPA)
  (log/info "Starting session machine.")
  (app/mount! SPA root/Root "app" {:initialize-state? false}))
