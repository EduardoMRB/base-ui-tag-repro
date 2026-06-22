(ns app.ui.root
  (:require
   [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
   [com.fulcrologic.fulcro.dom :as dom]
   [com.fulcrologic.fulcro.algorithms.react-interop :as react-interop]
   ["styletron-engine-monolithic" :refer [Client]]
   ["styletron-react" :refer [Provider]]
   ["baseui" :refer [LightTheme BaseProvider]]
   ["baseui/tag" :refer [Tag]]
   ["baseui/button" :refer [Button]]))

(def tag (react-interop/react-factory Tag))
(def base-ui-button (react-interop/react-factory Button))
(def engine (Client. #js {:prefix "_"}))
(def styletron-provider (react-interop/react-factory Provider))
(def base-provider (react-interop/react-factory BaseProvider))

(defsc Root [_this _props]
  {:use-hooks? :pure}
  (styletron-provider {:value engine}
    (base-provider {:theme LightTheme}
      (dom/div :.ui.container.segment
        (base-ui-button {:onClick (fn [] (js/console.log "click"))}
          "Other components work")
        ;; Comment the next line and see the button rendering just fine.
        (tag {:closeable false :variant "solid"} "Simple tag")))))
