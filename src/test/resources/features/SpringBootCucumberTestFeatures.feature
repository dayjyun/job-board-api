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