Feature: A player cannot play twice in a row
  As a user
  If I play on the board
  I cannot play again until the opposing player has made a move

Scenario: A player cannot play twice in a row
  Given I am a user
  And I choose to play a new game with the following preferences
    | marker  | size   | gofirst |
    | x       | 3      | y       |
  When I play on space 0
  And player "o" has not yet played
  And I play on space 1
  Then the response should be a 403