Feature: Edit Announcement

  Scenario: Successful editing of an announcement by an authenticated user
    Given the user is authenticated by site
    And an announcement has been previously created
    When the user edits the announcement with:
      | field       | new_value                |
      | name        | "Harry Potter and the Deathly Hallows"   |
      | description | "The final book in the series"    |
      | price       | 750                    |
    Then the announcement should be successfully updated
