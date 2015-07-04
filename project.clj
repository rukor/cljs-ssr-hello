(defproject hello "0.1.0"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :clean-targets ^{:protect false}
["resources/public/js/app.js" "resources/public/js/out" "target" "resources/server"
 "resources/index.js" "resources/index.js.map"]

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "0.0-3269"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [com.cognitect/transit-cljs "0.8.220"]
                 [org.omcljs/om "0.8.8"]
                 [reagent "0.5.0"]
                 [com.domkm/silk "0.0.4"]
                 [com.firstlinq/cljs-ssr-node "0.1.0"]]

  :plugins [[lein-cljsbuild "1.0.6"]
            [lein-environ "1.0.0"]
            [lein-less "1.7.2"]
            [lein-npm "0.4.0"]]

  :template-additions [".gitignore" "README.md" "Procfile"]

  :source-paths ["src"]
  :less {:source-paths ["src/less"]
         :target-path  "resources/public/css"}

  :cljsbuild
  {:builds
   {:app    {:source-paths ["src/app"]
             :compiler     {:main          "hello.app.main"
                            :output-to     "resources/public/js/app.js"
                            :output-dir    "resources/public/js/out"
                            :asset-path    "/js"
                            :pretty-print  true
                            :optimizations :none}}
    :server {:source-paths ["src/app", "src/server"]
             :compiler     {:target        :nodejs
                            :main          "hello.server.core"
                            :output-to     "resources/index.js"
                            :output-dir    "resources/server"
                            :preamble      ["include.js"]
                            :optimizations :simple
                            :pretty-print  true
                            :source-map    "resources/index.js.map"}}}}
  :profiles
  {:dev  {:cljsbuild {:builds
                      {:app {:compiler {:source-map true}}}}}
   :prod {:cljsbuild {:builds
                      {:app    {:compiler {:optimizations :advanced}}
                       :server {:compiler {:optimizations :simple}}}}}})
