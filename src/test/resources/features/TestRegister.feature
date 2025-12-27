Feature: User register functionality

  Scenario: User register on the main page
    Given the user is on the main page
    When the user clicks the "Вход и регистрация" button
    And the user clicks the "Нет аккаунта" button
    And the user enters email and password and confirm password
    And the user clicks the "Создать аккаунт" button
    Then the user is successfully logged in and sees their profile

  Scenario: Registration failure due to existing user
   Given the user is on the main page
    And the user is registered via API
    When the user clicks the "Вход и регистрация" button
    And the user clicks the "Нет аккаунта" button
    And the user enters email and password and confirm password
    And the user clicks the "Создать аккаунт" button
    Then the registration should fail with error message "Ошибка"