Feature: Rest API functionalities

#  Scenario: User is able to register
#    Given the user enters the proper information
#    When the user submits their details
#    Then the user can see their account details

#  Scenario: User is able to log in
#    Given the user enters their credentials
#    When the user submits their credentials
#    Then the user can see their JWT

  # PASS (PUBLIC)
  Scenario: User is able to view another user's account details
    Given A user account is available
    When I search for another user's id
    Then I can see the user's account details

  # PASS (PUBLIC)
  Scenario: User is able to see a list of all businesses
    Given A list of businesses are available
    When I search for businesses
    Then I can see a list of businesses

    # PASS (PRIVATE)
  Scenario: User is able to create a business
    Given A business name does not exist yet
    When I create a business with that name
    Then I can see my new business's details

    # PASS (PUBLIC)
  Scenario: User is able to view business details
    Given A business is available
    When I search by business id
    Then I can see a business's details

    # PASS (PRIVATE)
  Scenario: User is able to edit business details
    Given I can search for a business ID
    When I edit my business details
    Then I see the business is updated

    # PASS (PUBLIC)
  Scenario: User is able to see a list of job listings for a business
    Given A list of jobs is available for a business
    When I search for job listings for a business
    Then I can see a list of jobs for a business

   # PASS (PRIVATE)
  Scenario: User with business is able to create a job listing
    Given A business is available to create a job
    When I create a job listing
    Then I can see the new job listing's details

   # PASS (PUBLIC)
  Scenario: User is able to see a list of all jobs
    Given A list of jobs are available
    When I search for jobs
    Then I can see a list of jobs

    # PASS (PUBLIC)
  Scenario: User is able to see a specific job listing for a business
    Given A job listing is available
    When I search by job id
    Then I can see job listing details

    # PASS (PRIVATE)
  Scenario: User with business is able to edit job listing details
    Given I can search for a job ID
    When I edit my job details
    Then I see the job is updated

    # PASS (PRIVATE)
  Scenario: User is able to see a list of all applicants for their job
    Given A list of applicants is available
    When I view the list of applicants
    Then I can see the list of applicants

  # PASS (PRIVATE)
  Scenario: User is able to apply for a job
    Given A job listing is available
    When I apply for the job
    Then I see a message saying I have applied for the job

    # PASS (PUBLIC)
  Scenario: User with business is able to delete job listing
    Given A job listing is available
    When I delete a job from my Job list
    Then I can see my job listing is deleted

    # PASS (PRIVATE)
  Scenario: User is able to delete business
    Given A business is available
    When I delete a business from my Business list
    Then I can see my business is deleted

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