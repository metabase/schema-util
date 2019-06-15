(ns metabase.util.schema-test
  "Tests for utility schemas and various API helper functions."
  (:require [expectations :refer [expect]]
            [metabase.util.schema :as su]
            [schema.core :as s]))

;; check that the API error message generation is working as intended
(expect
  (str "value may be nil, or if non-nil, value must satisfy one of the following requirements: "
       "1) value must be a boolean. "
       "2) value must be a valid boolean string ('true' or 'false').")
  (str (su/api-error-message (s/maybe (s/cond-pre s/Bool su/BooleanString)))))

(expect
  nil
  (s/check (su/distinct [s/Int]) []))

(expect
  nil
  (s/check (su/distinct [s/Int]) [1]))

(expect
  nil
  (s/check (su/distinct [s/Int]) [1 2]))

(expect
  (some? (s/check (su/distinct [s/Int]) [1 2 1])))
