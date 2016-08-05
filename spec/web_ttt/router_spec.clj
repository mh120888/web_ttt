(ns web-ttt.router-spec
  (:require [speclj.core :refer :all]
    [web-ttt.action :as action]
    [web-ttt.router :refer :all])
  (:import [web_ttt.action StaticResourceAction]
   [web_ttt.action MethodNotAllowedAction]
   [web_ttt.action NewGameAction]
   [web_ttt.action UnprocessableEntityAction]
   [web_ttt.action NotFoundAction]))

(describe "route-and-return-action"
  (context "WHEN method = GET, path = /, params = {}"
    (it "returns a StaticResourceAction"
      (should-be-a (type (action/StaticResourceAction.)) (route-and-return-action "GET" "/" {}))))

  (context "WHEN method = !GET, path = /, params = (any)"
    (it "returns a MethodNotAllowedAction"
      (should-be-a (type (action/MethodNotAllowedAction.)) (route-and-return-action "POST" "/" {}))))

  (context "WHEN method = GET, path = /new-game, params include size, marker, and gofirst"
    (it "returns a NewGameAction"
      (should-be-a (type (action/NewGameAction.)) (route-and-return-action "GET" "/new-game" {"size" "3", "marker" "x", "gofirst" "y"}))))

  (context "WHEN method = GET, path = /new-game, params do not contain correct keys (size, marker, and gofirst)"
    (it "returns an UnprocessableEntityAction"
      (should-be-a (type (action/UnprocessableEntityAction.)) (route-and-return-action "GET" "/new-game" {}))))

  (context "unknown requests"
    (it "returns a NotFoundAction for requests to an unknown path"
      (should-be-a (type (action/NotFoundAction.)) (route-and-return-action "GET" "/fakepath" {})))))

(run-specs)