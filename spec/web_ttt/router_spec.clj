(ns web-ttt.router-spec
  (:require [speclj.core :refer :all]
    [web-ttt.action :as action]
    [web-ttt.router :refer :all])
  (:import [web_ttt.action HomepageAction]
   [web_ttt.action MethodNotAllowedAction]
   [web_ttt.action NewGameAction]
   [web_ttt.action MakeMoveAction]
   [web_ttt.action NotFoundAction]))

(describe "route-and-return-action"
  (context "WHEN method = GET, path = /"
    (it "returns a HomepageAction"
      (should-be-a (type (action/HomepageAction.)) (route-and-return-action "GET" "/"))))

  (context "WHEN method = !GET, path = /"
    (it "returns a MethodNotAllowedAction"
      (should-be-a (type (action/MethodNotAllowedAction.)) (route-and-return-action "POST" "/"))))

  (context "WHEN method = GET, path = /new-game"
    (it "returns a NewGameAction"
      (should-be-a (type (action/NewGameAction.)) (route-and-return-action "GET" "/new-game"))))

  (context "WHEN method = GET, path = /make-move"
    (it "returns a MakeMoveAction"
      (should-be-a (type (action/MakeMoveAction.)) (route-and-return-action "GET" "/make-move"))))

  (context "unknown requests"
    (it "returns a NotFoundAction for requests to an unknown path"
      (should-be-a (type (action/NotFoundAction.)) (route-and-return-action "GET" "/fakepath")))))

(run-specs)