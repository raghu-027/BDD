Feature: SauceDemo Product Purchase and Cart Management

  Scenario: Search, add multiple products, and complete checkout
    Given the user launches the SauceDemo website
    And the user logs in with "standard_user" and "secret_sauce"
    When the user adds "Sauce Labs Backpack" and "Sauce Labs Bike Light" to the cart
    And the user navigates to the cart page
    And the user removes "Sauce Labs Bike Light" from the cart
    Then the cart should show 1 item
    When the user proceeds to checkout
    And the user enters first name "raghu", last name "vamsi", and zip "533101"
    And the user clicks the Finish button
    Then an "Thank you for your order!" confirmation message should be displayed