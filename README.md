# job-board-api

# Endpoints
## Users
| Request Type | URL                 | Functionality                                       | Access  |
|--------------|---------------------|-----------------------------------------------------|---------|
| POST         | /api/auth/register  | User creates account                                | Public  |
| POST         | /api/auth/login     | User logs into account                              | Public  |
| GET          | /api/users/{userId} | Returns user account details                        | Private |
| GET          | /api/myProfile      | Returns logged-in user's details                    | Private |
| PUT          | /api/myProfile      | Edit user account                                   | Private |
| DELETE       | /api/myProfile      | Delete user account                                 | Private |
| GET          | /api/myProfile/jobs | Returns a list of jobs the user applied for         | Private |

## Businesses
| Request Type | URL                                       | Functionality                                       | Access  |
|--------------|-------------------------------------------|-----------------------------------------------------|---------|
| GET          | /api/businesses                           | Returns a list of all businesses                    | Public  |
| POST         | /api/businesses                           | Create a business                                   | Private |
| GET          | /api/businesses/{businessId}              | Returns business details                            | Public  |
| PUT          | /api/businesses/{businessId}              | Update business details                             | Private |
| DELETE       | /api/businesses/{businessId}              | Delete a business                                   | Private |
| GET          | /api/businesses/{businessId}/jobs         | Returns a list of all job listings for the business | Public  |
| POST         | /api/businesses/{businessId}/jobs         | Create a new job listing for the business           | Private |
| GET          | /api/businesses/{businessId}/jobs/{jobId} | Shows job listing from the business                 | Public  |
| PUT          | /api/businesses/{businessId}/jobs/{jobId} | Update job listing from the business                | Private |
| DELETE       | /api/businesses/{businessId}/jobs/{jobId} | Delete job listing from the business                | Private |

## Jobs
| Request Type | URL                       | Functionality                                       | Access  |
|--------------|---------------------------|-----------------------------------------------------|---------|
| GET          | /api/jobs                 | Returns all jobs in the job board                   | Public  |
| GET          | /api/jobs/{1}/applicants  | Returns a list of all applicants for the job        | Private |
| POST         | /api/jobs/{1}/applicants  | User applies for job                                | Private |
