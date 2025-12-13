Feature: User login functionality

  Scenario: User logs in via "Вход и регистрация" button on the main page
    Given the user is on the main page
    And the user is registered via API
    When the user clicks the "Вход и регистрация" button
    And the user enters email and password
    And the user clicks the "Войти" button
    Then the user is successfully logged in and sees their profile
