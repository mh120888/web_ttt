Feature: A user can make a move
  As a user
  I can choose a move on the board
  And see that my move was accepted

Scenario: A user can make a move
  Given I am a user
  And I choose to play a new game with the following preferences
    | marker  | size   | gofirst |
    | o       | 3      | y       |
  When I play on space 0
  Then the response should be a 200
  And the response should contain a board with space 0 taken by o