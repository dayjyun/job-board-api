# Job Board API

## Description
The Job Board API provides a platform for users to interact with job postings and businesses. It offers a range of features to meet the needs of job seekers and businesses looking for employees. The API allows users to create accounts, log in and manage their profile information. Registered users can apply for jobs, create job openings and track their applications. Businesses can view applicants for their job listings and manage their business information.

# Technologies

## Technologies Used

The Job Board API is built using the following technologies:
  - **Version Control**: Github is used for version control. The codebase can be found at [GitHub Repository](https://github.com/dayjyun/job-board-api/commits/main).
  - **Project Management**: [Github Projects](https://github.com/users/dayjyun/projects/7/views/1) is utlilized for project management and tracking progress.
  - **Documentation**: The API documentation is maintained in the GitHub Wiki, providing additional resources and information.
  - **Version Control System**: Git is used as a version control system.
  - **Web Browser**: The API is compatible with Google Chrome Browser.
  - **Documentation Tool**: Google Docs is used for documenting daily stand-ups and stand-downs.
  - **Entity Relationship Diagram (ERD)**: [Dbdiagram.io](https://dbdiagram.io/d/644ad886dca9fb07c42b4c62) is used to create the ERD for the database design.
  - **Database**: H2 database is used during development, accessible at [H2 Database Console](http://localhost:8080/h2-console/login.jsp).
  - **Secret Key Generation**: The secret key generator provided [GRC](https://www.grc.com/passwords.htm) is used to generate secure secret keys.
  - **Application Generator**: [Spring Initializer](https://start.spring.io/) is used to boostrap the project structure. The project is built using Maven.
  - **Programming Language**: The API is developed using Java 17.
  - **Framework**: The API is built on Spring Boot version 2.7.11.
  - **Packaging**: The API is packaged as a JAR files.
  - **API Testing**: POstman is used to test the API endpoints and manage the workspace.
  - **Markdown Table Generator**: The Markdown table generator available at [Tables Generator](https://www.tablesgenerator.com/markdown_tables) is used to create Markdown tables.

## Documentation
The documentation provides detailed explanations, usage examples, and external resources to support developers in effectively working with the Job Board API:
  - **Java API**: The official Java API documentation provided by Oracle can be accessed at [Java SE 17 Documentation](https://docs.oracle.com/en/java/javase/17/docs/api/index.html).
  - **Spring Framework Documentation**: The documentation for the Spring Framework can be found at [Spring Framework Reference Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/).
  - **Cucumber with Rest Assured**: Cucumber is used as a behavior-driven testing framework, integrating with Rest Assured for API testing. Information about Gherkin parsing language and usage can be found in the [Gherkin Documentation](https://cucumber.io/docs/gherkin/reference/#:~:text=Data%20Tables.-,Doc%20Strings,-Doc%20Strings%20are).
  - **JWT and Cucumber Integration**: Information on integrating JWT (JSON Web Tokens) with Cucumber for testing purposes is available in the [Spring Cucumber Rest API repository](https://github.com/RedFroggy/spring-cucumber-rest-api/blob/master/README.md?plain=1).
  - **Spring Security and H2 Database Access**: Stack Overflow provides a solution to allow access to the H2 Database Console when using Spring Security. The code snippet and explanation can be found at [Stack Overflow Answer](https://stackoverflow.com/questions/43794721/spring-boot-h2-console-throws-403-with-spring-security-1-5-2).

# General Approach 
To develop this API, the development team started by reviewing user stories and creating an ERD to visualize the database structure. The team followed a Test-Driven Development(TDD) to approach using Cucumber with Rest Assured, writing tests for each endpoint and implementing the minimum code required to make them pass. This helped to ensure that the API worked as expected and met the acceptance criteria.

The team utilized branching in Git with branches like Main, Dev, and Test. This allowed the team to work on seperate features and merge them into the appropriate branches. The team maintained seperate controllers and services, following the MVC design pattern, to ensure code organization and modularity. The team also focused on handling exceptions gracefully and providing clear error messages to users. 

The API offers CRUD operations for users, businesses, and jobs. The development team used Sprinf Security and JWT tokens for authentications and personlized access to endpoints. Most routes required authentication, except for user registration, login, and publicly accessible business and job information. To facilitate collaboration, the team used a Kanban board to track tasks and assigned roles like driver, navigator, tester, and pull request approver.

Progress has been documented in the README file, including project overview, tools used, and links to user stories, the ERD diagram, and planning documentation. Documentation was also provided for the REST API endpoints and provided installation instructions for dependencies. The team focused on iteratve development, TDD, and adherence to best practices. With efficient teamwork and project management techniques, the team successfully delivered a job board API.

# API Hurdles
During the development process, the team encountered a few hurdles that challeneged the progress of the API. Some of the specific issues we faced include:
 - Testing for `PUT` and `POST` requests: Objects were being created even when certain columns didn't exist. This reuired debugging and refining of code to ensure data integrity and proper handling of requests.
 - Adding a list to our seed data: There was difficulty incorporating a list of items into our seed data, which affected the accuracy of the tests. The team revisited a data setup and found a solution to include the required list data.
 - Testing private APIs with Cucumber: Cucumber needed to be set up to test private API's, which required more configuration and understanding of Cucumber. Different approaches were explored and refactoring of tests were made.
 - Resolving merge conflicts: As the team utlized branching, there were a few merge conflicts. Resolving these conflicts required effective communication and collaboration to ensure a smooth branch merging process.

Despite these hurdles, the team remained proactive and motivated to solving them, and ultimately overcame those hurdles to continue the development process.

# Endpoints
## Users
| Request Type | URL                      | Functionality                                       | Access  |
|--------------|--------------------------|-----------------------------------------------------|---------|
| POST         | /api/users/register      | User creates account                                | Public  |
| POST         | /api/users/login         | User logs into account                              | Public  |
| GET          | /users/{id}              | Returns user account details                        | Private |
| PUT          | /users/{id}              | Edit user account                                   | Private |
| DELETE       | /users/{id}              | Delete user account                                 | Private |
| GET          | /users/{id}/jobs         | Returns a list of jobs the user applied for         | Private |

## Business
| Request Type | URL                      | Functionality                                       | Access  |
|--------------|--------------------------|-----------------------------------------------------|---------|
| GET          | /businesses              | Returns a list of all businesses                    | Public  |
| POST         | /businesses              | Create a business                                   | Private |
| GET          | /businesses/{1}          | Returns business details                            | Public  |
| PUT          | /businesses/{1}          | Update business details                             | Private |
| DELETE       | /businesses/{1}          | Delete a business                                   | Private |
| GET          | /businesses/{1}/jobs     | Returns a list of all job listings for the business | Public  |
| POST         | /businesses/{1}/jobs     | Create a new job listing for the business           | Private |
| GET          | /businesses/{1}/jobs/{1} | Shows job listing from the business                 | Public  |
| PUT          | /businesses/{1}/jobs/{1} | Update job listing from the business                | Private |
| DELETE       | /businesses/{1}/jobs/{1} | Delete job listing from the business                | Private |

## Jobs
| Request Type | URL                      | Functionality                                       | Access  |
|--------------|--------------------------|-----------------------------------------------------|---------|
| GET          | /jobs                    | Returns all jobs in the job board                   | Public  |
| GET          | /jobs/{1}/applicants     | Returns a list of all applicants for the job        | Private |
| POST         | /jobs/{1}/applicants     | User applies for job                                | Private |
