Feature: Starting a new game of tic tac toe
  As a user
  I can start a new game with my game preferences
  And see a message
  So I know the game has started

Scenario: User creates a new game and sees a success message
  Given I am a user
  When I choose to play a new game with my preferences
  Then the response should be a 200
  And the response should contain the text "Let's play a game of tic tac toe"