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

  Scenario: User is able to see a list of all jobs
    Given A list of jobs are available
    When I search for jobs
    Then I can see a list of jobs

  Scenario: User is able to see a list of all applicants for their job
    Given A user account is available
    When I search by my job id
    Then I can see a list of applicants for my job
    When I have no jobs listed
    Then I see a message saying I have no jobs listed