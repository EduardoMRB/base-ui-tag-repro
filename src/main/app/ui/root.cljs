(ns app.ui.root
  (:require
   [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
   [com.fulcrologic.fulcro.dom :as dom :refer [b button div h3 li p ul]]
   [com.fulcrologic.fulcro.routing.dynamic-routing :as dr]
   [com.fulcrologic.fulcro.ui-state-machines :as uism :refer [defstatemachine]]
   [com.fulcrologic.fulcro.algorithms.react-interop :as react-interop]
   ["styletron-engine-monolithic" :refer [Client]]
   ["styletron-react" :refer [Provider]]
   ["baseui" :refer [LightTheme BaseProvider]]
   ["baseui/tag" :refer [Tag]]
   ["baseui/button" :refer [Button]]))

(def tag (react-interop/react-factory Tag))
(def base-ui-button (react-interop/react-factory Button))

(defsc Main [this props]
  {:query         [:main/welcome-message]
   :initial-state {:main/welcome-message "Hi!"}
   :ident         (fn [] [:component/id :main])
   :route-segment ["main"]
   :use-hooks?    :pure}
  (div :.ui.container.segment
    (h3 "Main")
    (p (str "Welcome to the Fulcro template. "
         "The Sign up and login functionalities are partially implemented, "
         "but mostly this is just a blank slate waiting "
         "for your project."))
    (base-ui-button {:onClick (fn [] (js/console.log "click"))}
      "Other components work")
    ;; Comment the next line and see the button rendering just fine.
    #_(tag {:closeable false :variant "solid"} "Simple tag")))

(dr/defrouter TopRouter [this props]
  {:router-targets [Main]})

(def ui-top-router (comp/factory TopRouter))

(defsc TopChrome [this {:root/keys [router]}]
  {:query         [{:root/router (comp/get-query TopRouter)}
                   [::uism/asm-id ::TopRouter]]
   :ident         (fn [] [:component/id :top-chrome])
   :use-hooks?    :pure
   :initial-state {:root/router {}}}
  (let [current-tab (some-> (dr/current-route this this) first keyword)]
    (div :.ui.container
      (div :.ui.grid
        (div :.ui.row
          (ui-top-router router))))))

(def ui-top-chrome (comp/factory TopChrome))

(def engine (Client. #js {:prefix "_"}))
(def styletron-provider (react-interop/react-factory Provider))
(def base-provider (react-interop/react-factory BaseProvider))

(defsc Root [this {:root/keys [top-chrome]}]
  {:query         [{:root/top-chrome (comp/get-query TopChrome)}]
   :use-hooks?    :pure
   :initial-state {:root/top-chrome {}}}
  (styletron-provider {:value engine}
    (base-provider {:theme LightTheme}
      (ui-top-chrome top-chrome))))
