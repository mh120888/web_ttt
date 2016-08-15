Feature: After finishing a game, the user is prompted to play another game
  As a user
  If a game is over
  I am given the option to play another game

Scenario: After finishing a game, the user is prompted to play another game
  Given I am a user
  And I choose to play a new game with the following preferences
    | marker  | size   | gofirst |
    | x       | 3      | y       |
  And the board is in the following state
  """
  x o x
  x o o
  o x _
  """
  When I play on space 8
  Then the response should contain the text "Do you want to play again?"