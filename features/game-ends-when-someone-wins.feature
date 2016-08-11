Feature: Game ends when there is a winner
  As a user
  If a game has a winner, then it is over

Scenario: Game ends when there is a winner
  Given I am a user
  And I choose to play a new game with the following preferences
    | marker  | size   | gofirst |
    | x       | 3      | y       |
  And the board is in the following state
  """
  _ o o
  o x o
  x o x
  """
  When I play on space 0
  Then the response should be a 200
  And the response should contain the text "Game Over"
  And the response should contain the text "Player x won"