@UI @Cart
Feature: Shopping Cart Functionality

  Background:
    Given User is logged in with valid credentials

  @Smoke
  Scenario: Add a product to the cart and verify it's added
    When User adds "Sauce Labs Backpack" to the cart
    And User opens the cart
    Then The cart badge should show 1 item
    And The product "Sauce Labs Backpack" should appear in the cart

  @Smoke
  Scenario: Add and remove a product from the cart
    When User adds "Sauce Labs Bike Light" to the cart
    And User opens the cart
    And User removes "Sauce Labs Bike Light" from the cart
    Then The cart should be empty

  @Negative
  Scenario: Try to add a non-existent product to the cart
    When User tries to add a non-existent product "Invisible Cloak 3000"
    And User opens the cart
    Then The cart badge should show 0 item
    And The product "Invisible Cloak 3000" should not appear in the cart

  @Negative
  Scenario: Remove a product that was never added
    When User removes "Sauce Labs Fleece Jacket" from the cart
    And User opens the cart
    Then The cart should be empty

  @Negative
  Scenario: View cart when no items are added
    When User opens the cart
    Then The cart should be empty

  # The second example demonstrates unexpected behavior:
  # after logging in as "problem_user", the cart still contains the item added by "standard_user".
  # This suggests cart state is not properly cleared or is shared across users.
  # Likely an intentional flaw for testing purposes, so the test is left as-is.
  Scenario Outline: Cart behavior after logout and re-login
    When User adds "Sauce Labs Backpack" to the cart
    And User logs out
    And User logs in with username "<username>" and password "secret_sauce"
    Then The cart badge should show <badge_count> item
    And The <cart_state>

    Examples:
      | username       | badge_count | cart_state                                              |
      | standard_user  | 1           | product "Sauce Labs Backpack" should appear in the cart |
      | problem_user   | 0           | cart should be empty                                    |
