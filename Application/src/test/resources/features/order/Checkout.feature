Feature: Checkout

  Background:
    Given a "CUSTOMER" is registered into the application with username "Khalifa" and password "P@$$W0RD"
    And customer "Khalifa" is signed in with password "P@$$W0RD"

  Scenario: 1. Checkout with a valid basket
    Given customer has the following items in basket
      | Item name        | Quantity | Price per item | Is item available |
      | Mix cheese pizza | 2        | 150            | true              |
      | Pepsi can        | 5        | 10             | true              |
    When customer checks-out the basket
    Then a payment-intent key and with total of EGP 350 are returned to the customer to fill credit card information
    When customer fills the credit card information and completes payment
    Then an event is received from the payment gateway notifying that the user completed the payment process
    And payment info for the user is updated in database
    And order is submitted successfully

  Scenario: 2.1. Checkout with an invalid basket (one item not available)
    Given customer has the following items in basket
      | Item name        | Quantity | Price per item | Is item available |
      | Mix cheese pizza | 2        | 150            | true              |
      | Pepsi can        | 5        | 10             | false             |
    When customer checks-out the basket
    Then an error is returned to the user with status code 400, minor code 12, and a message "Sorry, one or more items are not available, let's try something else"

  Scenario: 2.2. Checkout with an invalid basket (more than one item not available)
    Given customer has the following items in basket
      | Item name        | Quantity | Price per item | Is item available |
      | Mix cheese pizza | 2        | 150            | false             |
      | Pepsi can        | 5        | 10             | false             |
    When customer checks-out the basket
    Then an error is returned to the user with status code 400, minor code 12, and a message "Sorry, one or more items are not available, let's try something else"

  Scenario: 3. Checkout with an invalid basket (total Quantity is less than the minimum)
    Given customer has the following items in basket
      | Item name | Quantity | Price per item | Is item available |
      | Pepsi can | 5        | 10             | true              |
    When customer checks-out the basket
    Then an error is returned to the user with status code 400, minor code 13, and a message "Sure you're not that hungry?"

  Scenario: 4. Checkout with an invalid basket (total Quantity is more than the maximum)
    Given customer has the following items in basket
      | Item name        | Quantity | Price per item | Is item available |
      | Mix cheese pizza | 10       | 150            | true              |
      | Pepsi can        | 5        | 10             | true              |
    When customer checks-out the basket
    Then an error is returned to the user with status code 400, minor code 14, and a message "Having a party without me? let's break the order down into smaller orders"
