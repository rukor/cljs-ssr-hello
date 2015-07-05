(ns leiningen.packagejson
  (:require [leiningen.npm :as npm]
            [cheshire.core :as json]
            [clojure.java.io :as io]
            [leiningen.npm.deps :refer [resolve-node-deps]]))

;; inlined these bits from lein-npm as it keeps deleting the package.json after each action
;; this makes it difficult to deploy to heroku as a nodejs project.

(defn- root [project]
  (if-let [root (project :npm-root)]
    (if (keyword? root)
      (project root)                                        ;; e.g. support using :target-path
      root)
    (project :root)))

(defn packagejson [project & args]
  (println "Generating package.json")
  (->> (json/generate-string
         (merge {:private true}                           ;; prevent npm warnings about repository and README
                {:name         (project :name)
                 :description  (project :description)
                 :version      (project :version)
                 :dependencies (npm/transform-deps (resolve-node-deps project))}
                (when-let [main (project :main)]
                  {:scripts {:start (str "node " main)}})
                (project :nodejs))
         {:pretty true})
       (spit (io/file (root project) "package.json"))))