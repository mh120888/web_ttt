Feature: Starting a new game of tic tac toe
  As a user
  I can specify my game preferences
  And see a message
  So I know my preferences were accepted

Scenario: User creates a new game and sees a success message
  Given I am a user
  And I choose to play a new game with the following preferences
    | marker  | size   | gofirst |
    | o       | 3      | y       |
  Then the response should be a 200
  And the response should contain the text "Let's play a game of tic tac toe"
  And the response should contain an empty board of size 3