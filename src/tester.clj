(ns tester
  (:require [babashka.cli :as cli]))

(def global-spec {:config  {:desc "Config edn file to use"
                            :coerce []}
                  :verbose {:coerce :boolean}})
(def dns-spec {})
(def dns-get-spec {})
(def table
  [{:cmds []            :fn identity :spec global-spec}
   {:cmds ["dns"]       :fn identity :spec dns-spec}
   {:cmds ["dns" "get"] :fn identity :spec dns-get-spec}])

(defn prn-dispatch
  [& args]
  (apply prn :input args)
  (prn :output (cli/dispatch table args)))

(defn -main
  [& args]
  (prn-dispatch "--config" "config-dev.edn" "--config" "other.edn" "dns")
  (prn-dispatch "--config" "config-dev.edn" "--config" "other.edn" "dns" "get")
  (prn-dispatch "--config" "config-dev.edn" "--verbose" "--config" "other.edn" "dns" "get")
  (prn-dispatch "--config" "config-dev.edn" "--config=other.edn" "dns" "get"))

(when (= *file* (System/getProperty "babashka.file"))
  (apply -main *command-line-args*))
