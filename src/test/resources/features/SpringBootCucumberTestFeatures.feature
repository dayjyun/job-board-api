Feature: Rest API functionalities

  Scenario: User able to add and remove book
    Given A list of books are available
#    When I add a book to my reading list
#    Then The book is added
#    When I remove book from my reading list
#    Then The book is removed

  Scenario: User is able to view account details
    Given A user account is available
    When I search by my user id
    Then I can see my account details

  Scenario: User is able to edit account details
    Given A user account is available
    When I search by my user id
    Then I can edit my account details

  Scenario: User is able to delete account
    Given A user account is available
    When I search by my user id
    Then I can delete my account

  Scenario: User is able to see all jobs applied for
    Given A user account is available
    When I search by my user id
    Then I can see a list of jobs
    When No jobs have been applied for
    Then I see a message saying no jobs have been applied for

  Scenario: User is able to see a list of all businesses
    Given A list of businesses are available
    When I search for businesses
    Then I can see a list of businesses

  Scenario: User is able to create a business
    Given A user account is available
    When I create a business
    Then I can see my business details

  Scenario: User is able to view business details
    Given A business is available
    When I search by business id
    Then I can see a business's details

  Scenario: User is able to edit business details
    Given A business is available
    When I search by business id
    Then I can edit my business details
    When A business is not available
    Then I see a message saying business is not available

  Scenario: User is able to delete business
    Given A business is available
    When I search by business id
    Then I can delete my business
    When A business is not available
    Then I see a message saying business is not available

  Scenario: User is able to see a list of job listings for a business
    Given A list of jobs is available
    When I search for job listings within a business
    Then I can see a list of jobs for a business

  Scenario: User with business is able to create a job listing
    Given A business is available
    When I create a job listing
    Then I can see the job listing's details

  Scenario: User is able to see a specific job listing for a business
    Given A job listing is available
    When I search by job id
    Then I can see job listing details