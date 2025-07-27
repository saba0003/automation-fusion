@UI @Logout
Feature: Logout Functionality

  @Smoke
  Scenario: User successfully logs out from the application
    Given User is logged in with valid credentials
    When User clicks the menu button
    And User clicks the logout button
    Then User should be redirected to the login page

  @Negative
  Scenario: Attempt logout without being logged in
    Given User is on the login page
    When User tries to access the logout link directly
    Then Logout should not occur and user should remain on login page
