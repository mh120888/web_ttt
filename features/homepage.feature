Feature: Home Page
  As a user
  I want the user to be able to navigate to the homepage
  And see a welcome message
  So that they know they're on the right page

Scenario: User goes to homepage and sees the welcome message
  Given I am a user
  When I go to the homepage
  Then the response should be a 200
  And the response should contain the text "Do you want to play a game?"