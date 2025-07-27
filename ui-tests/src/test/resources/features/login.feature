@UI @Login
Feature: Login Functionality

  Background:
    Given User is on the login page

  @Smoke
  Scenario Outline: Login attempts with various credentials
    When User logs in with username "<username>" and password "<password>"
    Then <expected_result>

    Examples:
      | username                | password       | expected_result                                                                    |
      | standard_user           | secret_sauce   | User should be redirected to the products page                                     |
      | locked_out_user         | secret_sauce   | An error message "Sorry, this user has been locked out." should be displayed       |
      | problem_user            | secret_sauce   | User should be redirected to the products page                                     |
      | performance_glitch_user | secret_sauce   | User should be redirected to the products page                                     |
      | wrong_user              | wrong_password | An error message "Username and password do not match any user" should be displayed |
      |                         | secret_sauce   | An error message "Username is required" should be displayed                        |
      | standard_user           |                | An error message "Password is required" should be displayed                        |
      |                         |                | An error message "Username is required" should be displayed                        |

  @Negative
  Scenario: Attempt to access to the inventory page without being logged in
    When User navigates directly to the inventory page
    Then User should be redirected to the login page
    And An error message "You can only access '/inventory.html' when you are logged in." should be displayed

  @Negative
  Scenario: Attempt to access to the cart page without being logged in
    When User navigates directly to the cart page
    Then User should be redirected to the login page
    And An error message "You can only access '/cart.html' when you are logged in." should be displayed
