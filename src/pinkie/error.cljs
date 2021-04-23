(ns pinkie.error
  (:require
   [reagent.core :as reagent]
      ;[re-catch.core :as rc]
   ))


;; https://github.com/reagent-project/reagent/issues/466


(defn error-boundary [_ #_comp]
  (let [error (reagent/atom nil)
        info (reagent/atom nil)]
    (reagent/create-class
     {; #js {:componentStack 
       ; cmp@http://localhost:8000/r/cljs-runtime/reagent.impl.component.js:508:43
       ; cmp@http://localhost:8000/r/cljs-runtime/reagent.impl.component.js:508:43
       ; cmp@http://localhost:8000/r/cljs-runtime/reagent.impl.component.js:508:43
       ; div
      :componentDidCatch (fn [error info]
                           (println "cdc: " error info))

      :component-did-catch (fn [_ #_this _ #_e i]
                             (println "pinkie component did catch: " i)
                             (reset! info i))

      :get-derived-state-from-error (fn [e]
                                      (println "pinkie component get-derived-state-from-error: " e)
                                      (reset! error e)
                                      #js {})
      :reagent-render (fn [comp]
                        (if @error
                          [:div.bg-red-300
                           "Component Error: "
                           (if @error (pr-str @error))]
                          comp))})))

