(:require [web-ttt.core :refer :all])

(When #"^I go to the homepage$" []
  (comment  main  )
  (throw (cucumber.api.PendingException.)))

(Then #"^I should see the welcome message$" []
  (comment  Write code here that turns the phrase above into concrete actions  )
  (throw (cucumber.api.PendingException.)))