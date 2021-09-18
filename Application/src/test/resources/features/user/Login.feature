Feature: Login
  Background:
    Given a "CUSTOMER" is registered into the application with username "Khalifa" and password "P@$$W0RD"

  Scenario: 1. Login with valid credentials
    When a user tries to login with credentials "Khalifa" as username and "P@$$W0RD" as password
    Then a token is returned to the user as response

  Scenario: 2. Login with invalid credentials (incorrect password)
    When a user tries to login with credentials "Khalifa" as username and "password" as password
    Then an error is returned to the user with status code 400, minor code 11, and a message "Incorrect password"

  Scenario: 3. Login with invalid credentials (non-existing user)
    When a user tries to login with credentials "John" as username and "P@$$W0RD" as password
    Then an error is returned to the user with status code 404, minor code 2, and a message "User John not found"

  Scenario: 4. Login with invalid credentials (missing username)
    When a user tries to login with credentials "" as username and "P@$$W0RD" as password
    Then an error is returned to the user with status code 400, minor code 10, and a message "One or more fields are invalid in object CredentialsDTO"

  Scenario: 5. Login with invalid credentials (missing password)
    When a user tries to login with credentials "Khalifa" as username and "" as password
    Then an error is returned to the user with status code 400, minor code 10, and a message "One or more fields are invalid in object CredentialsDTO"
