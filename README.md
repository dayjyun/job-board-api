# job-board-api

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
