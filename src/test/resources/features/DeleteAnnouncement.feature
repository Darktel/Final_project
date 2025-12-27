Feature: Delete announcement

  Scenario: Successful deletion of an announcement by an authenticated user
    Given User creates a new account with valid credentials
    And an announcement has been created
    And is authorized user navigates to the profile page
    And the user scrolls down to the announcement
    When the user clicks the delete button for the announcement
    Then the announcement should no longer be visible on the profile page
