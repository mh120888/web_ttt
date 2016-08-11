Feature: A space that has been taken cannot be played on again
  As a user
  If a space has been taken
  It is not available to be played on again

Scenario: A space that has been taken cannot be played on again
  Given I am a user
  And I choose to play a new game with the following preferences
    | marker  | size   | gofirst |
    | x       | 3      | y       |
  When I play on space 0
  Then space 0 is not available to be played on again