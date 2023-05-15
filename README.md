# Job Board API

## Table of Contents
- [Description](#description)
- [Technologies](#technologies)
  - [Technologies Used](#technologies-used)
  - [Documentation](#documentation)
  - [Installation Instructions](#installation-instructions)
- [General Approach](#general-approach)
- [Hurdles](#hurdles) 
- [User Stories](#user-stories)
-  [Endpoints](#endpoints)
    -  [Users](#users)
    -  [Business](#business)
    -  [Jobs](#jobs)
- [Continuous Development](#continuous-development)
- [Acknowledgments](#acknowledgments)

---

# Description
The Job Board API provides a platform for users to interact with job listings and businesses. It offers a range of features to meet the needs of job-seekers and businesses looking for employees. 
* The API allows users to create accounts, log in, and manage their profile information. 
* Registered users can apply for jobs, create job listings, and track their applications. 
* Businesses can view applicants for their job listings and manage their business information.

---

# Technologies

## Technologies Used

The Job Board API is built using the following technologies:
  - **IDE**: IntelliJ is the integrated development environment for software development.
  - **Programming Language**: The API is developed using Java 17.
  - **Framework**: The API is built on Spring Boot version 2.7.8.
  - **Project Management**: [GitHub Projects](https://github.com/users/dayjyun/projects/7/views/1) is utilized for project management and tracking progress.
  - **Documentation Tool**: Google Docs is used for documenting daily stand-ups and stand-downs.
  - **Version Control**: GitHub is used for version control. The codebase can be found at [GitHub Repository](https://github.com/dayjyun/job-board-api/commits/main).
  - **Documentation**: The API documentation is maintained in the GitHub Wiki, providing additional resources and information.
  - **Entity Relationship Diagram (ERD)**: [Dbdiagram.io](https://dbdiagram.io/d/644ad886dca9fb07c42b4c62) is used to create the ERD for the database design.
  - **Database**: The [H2 Database Engine](https://www.geeksforgeeks.org/spring-boot-h2-database/) is used during development.
  - **Web Browser**: Google Chrome Browser was used to access the H2 Database Engine.
  - **Secret Key Generation**: The secret key generator provided by [GRC](https://www.grc.com/passwords.htm) is used to generate secure secret keys.
  - **Application Generator**: [Spring Initializer](https://start.spring.io/) is used to boostrap the project structure. The project is built using Maven.
     - **Packaging**: The spring boot application uses Jar packaging.
  - **API Testing**: [Postman](https://www.postman.com/) is used to test the API endpoints and manage the workspace.
  - **Markdown Table Generator**: The Markdown table generator available at [Tables Generator](https://www.tablesgenerator.com/markdown_tables) is used to create Markdown tables.

## Documentation
The documentation provides detailed explanations, usage examples, and external resources to support developers in effectively working with the Job Board API:
  - **Java API**: The official Java API documentation provided by Oracle can be accessed at [Java SE 17 Documentation](https://docs.oracle.com/en/java/javase/17/docs/api/index.html).
  - **Spring Framework Documentation**: The documentation for the Spring Framework can be found at [Spring Framework Reference Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/).
  - **Cucumber with Rest Assured**: Cucumber is used as a behavior-driven testing framework, integrating with Rest Assured for API testing. Information about Gherkin language and usage can be found in the [Gherkin Documentation](https://cucumber.io/docs/gherkin/reference/#:~:text=Data%20Tables.-,Doc%20Strings,-Doc%20Strings%20are).
  - **JWT and Cucumber Integration**: Information on integrating JWT (JSON Web Tokens) with Cucumber for testing purposes is available in the [Spring Cucumber Rest API repository](https://github.com/RedFroggy/spring-cucumber-rest-api/blob/master/README.md?plain=1).
  - **Spring Security and H2 Database Access**: Stack Overflow provides a solution to allow access to the H2 Database Console when using Spring Security. The code snippet and explanation can be found at [Stack Overflow Answer](https://stackoverflow.com/questions/43794721/spring-boot-h2-console-throws-403-with-spring-security-1-5-2).

# Installation Instructions
- Use the following links provided below to access the Maven Central repository.

<details>
<summary>List of Dependencies Used</summary>summary>

- [spring-boot-starter-data-rest](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-rest)
- [spring-boot-devtools](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-devtools)
- [h2](https://mvnrepository.com/artifact/com.h2database/h2)
- [spring-boot-starter-jdbc](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-jdbc)
- [spring-boot-starter-test](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-test)
- [spring-boot-starter-data-jpa](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa)
- [spring-boot-starter-security](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security)
- [junit](https://mvnrepository.com/artifact/junit/junit)
- [cucumber-java](https://mvnrepository.com/artifact/io.cucumber/cucumber-java)
- [cucumber-junit](https://mvnrepository.com/artifact/io.cucumber/cucumber-junit)
- [cucumber-spring](https://mvnrepository.com/artifact/io.cucumber/cucumber-spring)
- [rest-assured](https://mvnrepository.com/artifact/io.rest-assured/rest-assured/4.3.0)
- [spring-boot-starter-validation](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-validation/3.0.6)
- [jjwt-api](https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-api/0.11.5)
- [jjwt-impl](https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-impl/0.11.5)
- [jjwt-jackson](https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-jackson/0.11.5)

</details>

Copy and paste the dependencies into the pom.xml file within `<dependencies>`.

![](https://i.postimg.cc/fWG4ZDz0/Screenshot-22.png)

![](https://i.postimg.cc/mkmVWQsY/Screenshot-31.png)

- Refresh the Maven project and the dependencies should be added to your project
  ![](https://i.postimg.cc/BbSNzmdZ/Screenshot-30.png)

---

# General Approach
To develop this API, the development team started by reviewing user stories and creating an ERD to visualize the database structure. The team followed a Behavioral-Driven 
Development (BDD) approach using Cucumber with Rest Assured, writing tests for each endpoint and implementing the minimum code required to make them pass. This helped to ensure 
that the API worked as expected and met the acceptance criteria.

The team utilized branching in Git with branches like Main, Dev, and other feature branches. This allowed them to work on separate features and merge them into the appropriate 
branches. The team maintained separate controllers and services, following the MVC design pattern, to ensure code organization and modularity. They also focused on handling exceptions gracefully and providing clear error messages to users. 

The API offers CRUD operations for **Users**, **Businesses**, and **Jobs** models. The development team used Spring Security and JWT tokens for authentications and personalized 
access to endpoints. Most routes required authentication, except for a few such as user registration, login, and publicly accessible business and job information. To facilitate 
collaboration, they used a [Kanban board](https://github.com/users/dayjyun/projects/7) to track tasks and assigned roles like driver, navigator, tester, and approving pull requests.

Progress has been documented in the README file, including project overview, tools used, and links to user stories, the ERD diagram, and planning documentation. Documentation 
was also provided for the REST API endpoints and provided installation instructions for dependencies. The team focused on iterative development, TDD, and adherence to best practices. With efficient teamwork and project management techniques, the team successfully delivered a job board API.

---

# Hurdles
During the development process, the team encountered a few hurdles that challenged the progress of the API. Some of the specific issues that were faced include:
 - Testing for `PUT` and `POST` requests: Objects were being created even when certain columns didn't exist. This required debugging and refining of code to ensure data integrity and proper handling of requests.
 - Adding a list to our seed data: There was difficulty incorporating a list of items into our seed data, which affected the accuracy of the tests. The team revisited a data setup and found a solution to include the required list data.
 - Testing private APIs with Cucumber: Cucumber needed to be set up to test private APIs, which required more configuration and understanding of Cucumber. Different approaches were explored and refactoring of tests were made.
 - Resolving merge conflicts: As the team utilized branching, there were a few merge conflicts. Resolving these conflicts required effective communication and collaboration to 
   ensure a smooth branch merging process.

Despite these hurdles, the team remained proactive and motivated to solving them, and ultimately overcame those hurdles to continue the development process.

---

# User Stories
The [User Stories](https://github.com/dayjyun/job-board-api/wiki) highlight an excellent representation of the thinking process and ideas of what the application should accomplish.

---

# Endpoints

## Users

| Request Type | URL                 | Functionality                               | Access  |
|--------------|---------------------|---------------------------------------------|---------|
| POST         | /api/auth/register  | User creates account                        | Public  |
| POST         | /api/auth/login     | User logs into account                      | Public  |
| GET          | /api/users/{userId} | Returns user account details                | Public  |
| GET          | /api/myProfile      | Returns logged-in user's details            | Private |
| PUT          | /api/myProfile      | Edit user account                           | Private |
| DELETE       | /api/myProfile      | Delete user account                         | Private |
| GET          | /api/myProfile/jobs | Returns a list of jobs the user applied for | Private |

## Business

| Request Type | URL                               | Functionality                                       | Access  |
|--------------|-----------------------------------|-----------------------------------------------------|---------|
| GET          | /api/businesses                   | Returns a list of all businesses                    | Public  |
| POST         | /api/businesses                   | Create a business                                   | Private |
| GET          | /api/businesses/{businessId}      | Returns business details                            | Public  |
| PUT          | /api/businesses/{businessId}      | Update business details                             | Private |
| DELETE       | /api/businesses/{businessId}      | Delete a business                                   | Private |
| GET          | /api/businesses/{businessId}/jobs | Returns a list of all job listings for the business | Public  |
| POST         | /api/businesses/{businessId}/jobs | Create a new job listing for the business           | Private |

## Jobs

| Request Type | URL                          | Functionality                                | Access  |
|--------------|------------------------------|----------------------------------------------|---------|
| GET          | /api/jobs                    | Returns all jobs in the job board            | Public  |
| GET          | /api/jobs/{jobId}            | Returns job listing details                  | Public  |
| PUT          | /api/jobs{jobId}             | Update job listing details                   | Private |
| DELETE       | /api/jobs/{jobId}            | Delete job listing                           | Private |
| GET          | /api/jobs/{jobId}/applicants | Returns a list of all applicants for the job | Private |
| POST         | /api/jobs/{jobId}/applicants | User applies for job                         | Private |

---

# Continuous Development
Just as professionals develop within their career, especially software engineers, so should the platform which helps level-up your career, too.
- [ ] Display jobs based on salary ranges
- [ ] Display jobs based on role seniority
- [ ] Search through different job types
- [ ] Develop the frontend portion
   - [ ] User images
   - [ ] Connecting with users by having a _Friends_ or _Connections_ table

---

# Acknowledgments
Development team
- **Kevin Barrios**: [GitHub Profile](https://github.com/dayjyun) | [LinkedIn Profile](https://www.linkedin.com/in/kevinbarrios12/)
- **Kim Nguyen**: [GitHub Profile](https://github.com/knnguyen2410) | [LinkedIn Profile](https://www.linkedin.com/in/knnguyen2410/)
- **DeShe Woods**: [GitHub Profile](https://github.com/woodsdeshe) | [LinkedIn Profile](https://www.linkedin.com/in/deshe-woods-tech-elevator/)

Helped development team in creating seed data
- **Maksym Zinchenko**: [GitHub profile](https://github.com/maklaut007)

Helped development team with debugging code
- **Suresh Sigera**: [GitHub profile](https://github.com/sureshmelvinsigera)

[Go to top](#job-board-api)