Feature: Job Board API functionalities

  # PUBLIC
  Scenario: User is able to view another user's account details
    Given A user account is available
    When I search for another user's id
    Then I can see the user's account details

  # PRIVATE
  Scenario: User is able to see all jobs applied for
    Given I have a list of jobs I have applied to
    When I search for list of jobs I applied to
    Then I can see a list of jobs I applied to

  # PUBLIC
  Scenario: User is able to see a list of all businesses
    Given A list of businesses are available
    When I search for businesses
    Then I can see a list of businesses

  # PRIVATE
  Scenario: User is able to create a business
    Given A business name does not exist yet
    When I create a business with that name
    Then I can see my new business's details

  # PUBLIC
  Scenario: User is able to view business details
    Given A business is available
    When I search by business id
    Then I can see a business's details

  # PRIVATE
  Scenario: User is able to edit business details
    Given I can search for a business ID
    When I edit my business details
    Then I see the business is updated

  # PUBLIC
  Scenario: User is able to see a list of job listings for a business
    Given A list of jobs is available for a business
    When I search for job listings for a business
    Then I can see a list of jobs for a business

  # PRIVATE
  Scenario: User with business is able to create a job listing
    Given A business is available to create a job
    When I create a job listing
    Then I can see the new job listing's details

  # PUBLIC
  Scenario: User is able to see a list of all jobs
    Given A list of jobs are available
    When I search for jobs
    Then I can see a list of jobs

  # PUBLIC
  Scenario: User is able to see a specific job listing for a business
    Given A job listing is available
    When I search by job id
    Then I can see job listing details

  # PRIVATE
  Scenario: User with business is able to edit job listing details
    Given I can search for a job ID
    When I edit my job details
    Then I see the job is updated

  # PRIVATE
  Scenario: User is able to see a list of all applicants for their job
    Given A list of applicants is available
    When I view the list of applicants
    Then I can see the list of applicants

  # PRIVATE
  Scenario: User is able to apply for a job
    Given A job listing is available
    When I apply for the job
    Then I see a message saying I have applied for the job

  # PUBLIC
  Scenario: User with business is able to delete job listing
    Given A job listing is available
    When I delete a job from my Job list
    Then I can see my job listing is deleted

  # PRIVATE
  Scenario: User is able to delete business
    Given A business is available
    When I delete a business from my Business list
    Then I can see my business is deleted

  # PRIVATE
  Scenario: User is able to view their account details
    Given my account is available
    When I go to my profile
    Then I can see my account details

  # PRIVATE
  Scenario: User is able to edit their account details
    Given my account is available
    When I edit my profile
    Then I see my profile is updated

