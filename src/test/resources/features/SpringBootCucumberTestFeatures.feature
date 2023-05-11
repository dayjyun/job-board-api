Feature: Rest API functionalities
#
#  Scenario: User is able to view another user's account details
#    Given A user account is available
#    When I search for another user's id
#    Then I can see the user's account details

#  Scenario: User is able to view account details
#    Given A user account is available
#    When I go to my profile
#    Then I can see my account details
#
#  Scenario: User is able to edit account details
#    Given A user account is available
#    When I go to my profile
#    Then I can edit my account details
#
#  Scenario: User is able to delete account
#    Given A user account is available
#    When I go to my profile
#    Then I can delete my account
#
#  Scenario: User is able to see all jobs applied for
#    Given A user account is available
#    When I search by my user id
#    Then I can see a list of jobs
#    When No jobs have been applied for
#    Then I see a message saying no jobs have been applied for
#

  Scenario: User is able to see a list of all businesses
    Given A list of businesses are available
    When I search for businesses
    Then I can see a list of businesses

  Scenario: User is able to create a business
    Given A business name does not exist yet
    When I create a business with that name
    Then I can see my new business's details

#  Scenario: User is able to view business details
#    Given A business is available
#    When I search by business id
#    Then I can see a business's details
#
  Scenario: User is able to edit business details
    Given I can search for a business ID
    When I edit my business details
    Then I see the business is updated
#    Given A business account is available
#    When I search for a business account
#    Then I can see the business account's details
#    When I edit the business details
#    Then I can see the business is updated
#
  Scenario: User is able to delete business
    Given A business is available
    When I delete a business from my Business list
    Then I can see my business is deleted


  Scenario: User is able to see a list of job listings for a business
    Given A list of jobs is available
    When I search for job listings within a business
    Then I can see a list of jobs for a business
#
#  Scenario: User with business is able to create a job listing
#    Given A business is available
#    When I create a job listing
#    Then I can see the job listing's details
#
#  Scenario: User is able to see a specific job listing for a business
#    Given A job listing is available
#    When I search by job id
#    Then I can see job listing details
#
#  Scenario: User with business is able to edit job listing details
#    Given A job listing is available
#    When I search by job id
#    Then I can edit the job listing details
#
#  Scenario: User with business is able to delete job listing
#    Given A job listing is available
#    When I search by job id
#    Then I can delete the job listing
#
#  Scenario: User is able to see a list of all jobs
#    Given A list of jobs are available
#    When I search for jobs
#    Then I can see a list of jobs
#
#  Scenario: User is able to see a list of all applicants for their job
#    Given A user account is available
#    When I search by my job id
#    Then I can see a list of applicants for my job
#    When I have no jobs listed
#    Then I see a message saying I have no jobs listed
#
#  Scenario: User is able to apply for a job
#    Given A user account is available
#    When I apply for the job
#    Then I see a message saying I have applied for the job