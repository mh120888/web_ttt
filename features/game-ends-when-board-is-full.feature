Feature:
  As a user
  If I play on the board's last available space, the game is over

Scenario:
  Given I am a user
  And I choose to play a new game with the following preferences
    | marker  | size   | gofirst |
    | x       | 3      | y       |
  And the board is in the following state
  """
  x o x
  o x o
  x o _
  """
  When I play on space 8
  Then the response should contain the text "Game Over"
  And the response should contain the text "Cat's Game"