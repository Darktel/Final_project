Feature: Announcement publishing

  Scenario: Successful announcement publication by an authenticated user
    Given the user is authenticated
    And the user is on the announcement creation page
    When the user fills in the announcement form:
      | field       | value                     |
      | title       | "The Lord of the Rings"   |
      | category    | "Books"                   |
      | condition   | "New"                     |
      | city        | "Kazan"                   |
      | description | "New edition, never opened"|
      | price       | 500                     |
    And the user uploads photo 2
    And the user clicks the "Publish" button
    Then the announcement is successfully published and visible on profile page
