package definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import jobboardapi.JobBoardApiApplication;
import jobboardapi.models.Business;
import jobboardapi.repository.BusinessRepository;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = JobBoardApiApplication.class)
public class JobBoardDefinitions {

   private static final String BASE_URL = "http://localhost:";
   private static Response response;
   private static ResponseEntity<String> responseEntity;
   private static List<?> list;
   private static RequestSpecification request;

   private static final String newBusinessName = "New Business Name";
   private static final String newJobNameForBusiness = "New Job Name For Business";

   public String getSecurityKey() throws Exception {
      RequestSpecification request = RestAssured.given();
      JSONObject requestBody = new JSONObject();
      requestBody.put("email", "deshe@gmail.com");
      requestBody.put("password", "pw");
      request.header("Content-Type", "application/json");
      response = request.body(requestBody.toString()).post(BASE_URL + port + "/api/users/login");
      return response.jsonPath().getString("message");
   }

   @Autowired
   private BusinessRepository businessRepository;

   @LocalServerPort
   String port;

   /**
    * Test Scenario: User is able to view another user's account details
    * Path: GET http://localhost:8080/api/users/{userId}
    * aUserAccountIsAvailable gets the user object from the specified endpoint
    * iSearchForAnotherUserSId checks that the user object is not null
    * iCanSeeTheUserSAccountDetails makes sure that the HTTP status is 200 when we successfully find the user object
    */
   @Given("A user account is available")
   public void aUserAccountIsAvailable() {
      RestAssured.baseURI = BASE_URL;
      RequestSpecification request = RestAssured.given();
      response = request.get(BASE_URL + port + "/api/users/1");
   }

   @When("I search for another user's id")
   public void iSearchForAnotherUserSId() {
      Assert.assertNotNull(String.valueOf(response));
   }

   @Then("I can see the user's account details")
   public void iCanSeeTheUserSAccountDetails() {
      Assert.assertEquals(200, response.getStatusCode());
   }

   /**
    * Scenario: User is able to see all jobs applied for
    * Path: GET http://localhost:8080/api/myProfile/jobs
    * iHaveAListOfJobsIHaveAppliedTo gets the list of all Jobs the logged-in user has applied to
    * iSearchForListOfJobsIAppliedTo checks that there is a list of jobs the logged-in user has applied to
    * iCanSeeAListOfJobsIAppliedTo makes sure the HTTP status code is 200 when we successfully find the list of jobs the logged-in user
    * has applied to
    */
   @Given("I have a list of jobs I have applied to")
   public void iHaveAListOfJobsIHaveAppliedTo() throws Exception {
      HttpHeaders headers = new HttpHeaders();
      headers.setBearerAuth(getSecurityKey());
      HttpEntity<String> entity = new HttpEntity<>(null, headers);

      responseEntity = new RestTemplate().exchange(BASE_URL + port + "/api/myProfile/jobs", HttpMethod.GET, entity, String.class);
      list = JsonPath.from(String.valueOf(responseEntity.getBody())).get();
   }

   @When("I search for list of jobs I applied to")
   public void iSearchForListOfJobsIAppliedTo() {
      Assert.assertTrue(list.size() > 0);
   }

   @Then("I can see a list of jobs I applied to")
   public void iCanSeeAListOfJobsIAppliedTo() {
      Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
   }

   /**
    * Test Scenario: User is able to see a list of all businesses
    * Path: GET http://localhost:8080/api/businesses
    * aListOfBusinessesAreAvailable gets the list of all businesses from the database referenced by the endpoint
    * iSearchForBusinesses checks that there is a list of businesses containing at least one business
    * iCanSeeAListOfBusinesses makes sure that the HTTP status is 200 when we successfully find the list of businesses
    */
   @Given("A list of businesses are available")
   public void aListOfBusinessesAreAvailable() {
      responseEntity = new RestTemplate().exchange(BASE_URL + port + "/api/businesses", HttpMethod.GET, null, String.class);
      list = JsonPath.from(String.valueOf(responseEntity.getBody())).get();
   }

   @When("I search for businesses")
   public void iSearchForBusinesses() {
      Assert.assertTrue(list.size() > 0);
   }

   @Then("I can see a list of businesses")
   public void iCanSeeAListOfBusinesses() {
      Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
   }

   /**
    * Test Scenario: User is able to create a business
    * Path: POST http://localhost:8080/api/businesses
    * aBusinessNameDoesNotExistYet checks the business database to see if the business name exists yet
    * iCreateABusinessWithThatName creates the business JSON object and posts it to the endpoint
    * iCanSeeMyNewBusinessSDetails makes sure that the HTTP status is 201 when we successfully create the businesses
    */
   @Given("A business name does not exist yet")
   public void aBusinessNameDoesNotExistYet() {
      Optional<Business> existingBusiness = businessRepository.findByName(newBusinessName);
      Assert.assertTrue(existingBusiness.isEmpty());
   }

   @When("I create a business with that name")
   public void iCreateABusinessWithThatName() throws Exception {
      RestAssured.baseURI = BASE_URL;
      RequestSpecification request = RestAssured.given().header("Authorization", "Bearer " + getSecurityKey());
      JSONObject requestBody = new JSONObject();
      requestBody.put("name", newBusinessName);
      requestBody.put("headquarters", "New Business Headquarters");
      request.header("Content-Type", "application/json");
      response = request.body(requestBody.toString()).post(BASE_URL + port + "/api/businesses");
   }

   @Then("I can see my new business's details")
   public void iCanSeeMyNewBusinessSDetails() {
      Assert.assertEquals(201, response.getStatusCode());
   }

   /**
    * Testing for Scenario: User is able to view business details
    * This is the GET request at the endpoint http://localhost:8080/api/businesses/{businessId}
    * aBusinessIsAvailable gets the business object from the specified endpoint
    * iSearchByBusinessId checks that the business object is not null
    * iCanSeeABusinessSDetails makes sure that the HTTP status is 200 when we successfully find the business object
    */
   @Given("A business is available")
   public void aBusinessIsAvailable() {
      RestAssured.baseURI = BASE_URL;
      RequestSpecification request = RestAssured.given();
      response = request.get(BASE_URL + port + "/api/businesses/1");
   }

   @When("I search by business id")
   public void iSearchByBusinessId() {
      Assert.assertNotNull(String.valueOf(response));
   }

   @Then("I can see a business's details")
   public void iCanSeeABusinessSDetails() {
      Assert.assertEquals(200, response.getStatusCode());
   }

   /**
    * Test Scenario: User is able to edit business details
    * Path: PUT http://localhost:8080/api/businesses/{businessId}
    * aBusinessIsAvailable sets request URL path for the business
    * iSearchByBusinessId updates the business details
    * iCanEditMyBusinessDetails confirms a successful update for the business
    */
   @Given("I can search for a business ID")
   public void aBusinessIsAvailablePUT() throws Exception {
      RestAssured.baseURI = BASE_URL;
      request = RestAssured.given();
      request.header("Authorization", "Bearer " + getSecurityKey());
   }

   @When("I edit my business details")
   public void iSearchByBusinessIdPUT() throws Exception {
      JSONObject requestBody = new JSONObject();
      requestBody.put("name", "Updated name");
      requestBody.put("headquarters", "Updated headquarters");
      request.header("Content-Type", "application/json");
      request.header("Authorization", "Bearer " + getSecurityKey());
      response = request.body(requestBody.toString()).put(BASE_URL + port + "/api/businesses/1");
   }

   @Then("I see the business is updated")
   public void iCanEditMyBusinessDetailsPUT() {
      Assert.assertEquals(200, response.getStatusCode());
   }

   /**
    * Test Scenario: User is able to see a list of job listings for a business
    * Path: GET http://localhost:8080/api/businesses/{businessId}/jobs
    * aListOfJobsIsAvailableForABusiness gets the list of all jobs from the business id = 1, as referenced by the endpoint
    * iSearchForJobListingsForABusiness checks that there is a list of jobs containing at least one job
    * iCanSeeAListOfJobsForABusiness makes sure that the HTTP status is 200 when we successfully find the list of jobs
    */
   @Given("A list of jobs is available for a business")
   public void aListOfJobsIsAvailableForABusiness() {
      responseEntity = new RestTemplate().exchange(BASE_URL + port + "/api/businesses/1/jobs", HttpMethod.GET, null, String.class);
      list = JsonPath.from(String.valueOf(responseEntity.getBody())).get();
   }

   @When("I search for job listings for a business")
   public void iSearchForJobListingsForABusiness() {
      Assert.assertTrue(list.size() > 0);
   }

   @Then("I can see a list of jobs for a business")
   public void iCanSeeAListOfJobsForABusiness() {
      Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
   }

   /**
    * Test Scenario: User with business is able to create a job listing
    * Path: POST http://localhost:8080/api/businesses/{1}/jobs
    * aBusinessIsAvailableToCreateAJob gets the business object from the specified endpoint
    * iCreateAJobListing creates the job JSON object and posts it to the endpoint
    * iCanSeeTheNewJobListingSDetails makes sure that the HTTP status is 201 when we successfully create the businesses
    */
   @Given("A business is available to create a job")
   public void aBusinessIsAvailableToCreateAJob() throws Exception {
      RestAssured.baseURI = BASE_URL;
      request = RestAssured.given().header("Authorization", "Bearer " + getSecurityKey());
      response = request.get(BASE_URL + port + "/api/businesses/1/jobs");
   }

   @When("I create a job listing")
   public void iCreateAJobListing() throws Exception {
      RestAssured.baseURI = BASE_URL;
      request = RestAssured.given();
      JSONObject requestBody = new JSONObject();
      requestBody.put("title", newJobNameForBusiness);
      requestBody.put("description", "New Job Description");
      requestBody.put("location", "New Job Location");
      requestBody.put("salary", 120000.00);
      request.header("Content-Type", "application/json");
      request.header("Authorization", "Bearer " + getSecurityKey());
      response = request.body(requestBody.toString()).post(BASE_URL + port + "/api/businesses/1/jobs");
      System.out.println(requestBody);
   }

   @Then("I can see the new job listing's details")
   public void iCanSeeTheNewJobListingSDetails() {
      Assert.assertEquals(201, response.getStatusCode());
   }

   /**
    * Test Scenario: User is able to see a list of all jobs
    * Path: GET http://localhost:8080/api/jobs
    * aListOfJobsIsAvailable gets the list of all jobs from the database referenced by the endpoint
    * iSearchForJobListingsWithinABusiness checks that there is a list of jobs containing at least on job
    * iCanSeeAListOfJobs makes sure the HTTP status is 200 when we successfully find the list of jobs
    */
   @Given("A list of jobs are available")
   public void aListOfJobsIsAvailable() {
      responseEntity = new RestTemplate().exchange(BASE_URL + port + "/api/jobs", HttpMethod.GET, null, String.class);
      list = JsonPath.from(String.valueOf(responseEntity.getBody())).get();
   }

   @When("I search for jobs")
   public void iSearchForJobListingsWithinABusiness() {
      Assert.assertTrue(list.size() > 0);
   }

   @Then("I can see a list of jobs")
   public void iCanSeeAListOfJobs() {
      Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
   }

   /**
    * Test Scenario: User is able to see a specific job listing for a business
    * Path: GET http://localhost:8080/api/jobs/{jobId}
    * aJobListingIsAvailable gets the job object from the specified endpoint
    * iSearchByJobId checks that the job object is not null
    * iCanSearchForAJobID makes sure that the HTTP status is 200 when we successfully find the job object
    */
   @Given("A job listing is available")
   public void aJobListingIsAvailable() {
      RestAssured.baseURI = BASE_URL;
      request = RestAssured.given();
      response = request.get(BASE_URL + port + "/api/jobs/1");
   }

   @When("I search by job id")
   public void iSearchByJobId() {
      Assert.assertNotNull(String.valueOf(response));
   }

   @Then("I can see job listing details")
   public void iCanSeeJobListingDetails() {
      Assert.assertEquals(200, response.getStatusCode());
   }

   /**
    * Test Scenario: User with business is able to edit job listing details
    * Path: PUT http://localhost:8080/api/jobs/{jobId}
    * iCanSearchForAJobID sets request URL path for the job
    * iEditMyJobDetails updates the job details
    * iSeeTheJobIsUpdated confirms a successful update for the job
    */

   @Given("I can search for a job ID")
   public void iCanSearchForAJobID() throws Exception {
      RestAssured.baseURI = BASE_URL;
      request = RestAssured.given();
      request.header("Authorization", "Bearer " + getSecurityKey());
   }

   @When("I edit my job details")
   public void iEditMyJobDetails() throws Exception {
      JSONObject requestBody = new JSONObject();
      requestBody.put("title", "Updated job title");
      requestBody.put("description", "Updated job description");
      requestBody.put("location", "Updated job location");
      requestBody.put("salary", 0.00);
      request.header("Content-Type", "application/json");
      request.header("Authorization", "Bearer " + getSecurityKey());
      response = request.body(requestBody.toString()).put(BASE_URL + port + "/api/jobs/1");
   }

   @Then("I see the job is updated")
   public void iSeeTheJobIsUpdated() {
      Assert.assertEquals(200, response.getStatusCode());
   }

   /**
    * Test Scenario: User is able to see a list of all applicants for their job
    * Path: GET http://localhost:8080/api/jobs/1/applicants
    * aListOfApplicantsIsAvailable gets the list of all Users from the database referenced by the endpoint
    * iViewTheListOfApplicants checks that there is a list of jobs containing at least on job
    * iCanSeeTheListOfApplicants makes sure the HTTP status code is 200 when we successfully find the list of jobs
    */
   @Given("A list of applicants is available")
   public void aListOfApplicantsIsAvailable() throws Exception {
      HttpHeaders headers = new HttpHeaders();
      headers.setBearerAuth(getSecurityKey());
      HttpEntity<String> entity = new HttpEntity<>(null, headers);

      responseEntity = new RestTemplate().exchange(BASE_URL + port + "/api/jobs/1/applicants", HttpMethod.GET, entity, String.class);
      list = JsonPath.from(String.valueOf(responseEntity.getBody())).get();
   }

   @When("I view the list of applicants")
   public void iViewTheListOfApplicants() {
      Assert.assertTrue(list.size() > 0);
   }

   @Then("I can see the list of applicants")
   public void iCanSeeTheListOfApplicants() {
      Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
   }

   /**
    * est Scenario: User is able to apply for a jobTest Scenario: User is able to apply for a job
    * Path: http://localhost:8080/api/jobs/1/applicants
    * iApplyForTheJob submits a User JSON object and posts it to the endpoint
    * iSeeAMessageSayingIHaveAppliedForTheJob Returns the job details
    */

   @When("I apply for the job")
   public void iApplyForTheJob() throws Exception {
      RestAssured.baseURI = BASE_URL;
      request = RestAssured.given().header("Authorization", "Bearer " + getSecurityKey());
      JSONObject requestBody = new JSONObject();
      requestBody.put("id", 1L);
      requestBody.put("name", "Logged-in user's name");
      requestBody.put("email", "logged-in user's email");
      requestBody.put("resume", "logged-in user's resume");
      request.header("Content-Type", "application/json");
      response = request.body(requestBody.toString()).post(BASE_URL + port + "/api/jobs/1/applicants");
   }

   @Then("I see a message saying I have applied for the job")
   public void iSeeAMessageSayingIHaveAppliedForTheJob() {
      Assert.assertEquals(200, response.getStatusCode());
   }

   /**
    * Test Scenario: User with business is able to delete job listing
    * Path: DELETE http://localhost:8080/api/jobs/{jobId}
    * iDeleteAJobFromMyJobList gets the job from the specified endpoint and sends the delete request to delete the job
    * iCanSeeMyJobListingIsDeleted makes sure that the HTTP status is 200 when we successfully delete the job object
    */
   @When("I delete a job from my Job list")
   public void iDeleteAJobFromMyJobList() throws Exception {
      request.header("Content-Type", "application/json");
      request.header("Authorization", "Bearer " + getSecurityKey());
      response = request.delete(BASE_URL + port + "/api/jobs/1");
   }

   @Then("I can see my job listing is deleted")
   public void iCanSeeMyJobListingIsDeleted() {
      Assert.assertEquals(200, response.getStatusCode());
   }

   /**
    * Testing for Scenario: User is able to delete business
    * This is the DELETE request at the endpoint http://localhost:8080/api/businesses/{businessId}
    * iDeleteBusinessFromMyBusinessList gets the business from the specified endpoint and sends the delete request to delete the business
    * iCanSeeMyBusinessIsDeleted makes sure that the HTTP status is 200 when we successfully delete the business object
    */
   @When("I delete a business from my Business list")
   public void iDeleteBusinessFromMyBusinessList() throws Exception {
      RestAssured.baseURI = BASE_URL;
      request = RestAssured.given().header("Authorization", "Bearer " + getSecurityKey());

      response = request.delete(BASE_URL + port + "/api/businesses/1");

      System.out.println("response"+response);
      System.out.println("request"+request);
   }

   @Then("I can see my business is deleted")
   public void iCanSeeMyBusinessIsDeleted() {
      Assert.assertEquals(200, response.getStatusCode());
   }

   /**
    * Scenario: User is able to view their account details
    * Path: GET http://localhost:8080/api/myProfile
    * aMyAccountIsAvailable gets the current logged-in user's object
    * iGoToMyProfile checks that the logged-in user's object is not null
    * iCanSeeMyAccountDetails makes sure that the HTTP status code is 200 when successfully returning the logged-in user's object
    */
   @Given("my account is available")
   public void aMyAccountIsAvailable() throws Exception {
      RestAssured.baseURI = BASE_URL;
      request = RestAssured.given().header("Authorization", "Bearer " + getSecurityKey());
      response = request.get(BASE_URL + port + "/api/myProfile");
   }

   @When("I go to my profile")
   public void iGoToMyProfile() {
      Assert.assertNotNull(String.valueOf(response));
   }

   @Then("I can see my account details")
   public void iCanSeeMyAccountDetails() {
      Assert.assertEquals(200, response.getStatusCode());
   }

   /**
    * Scenario: User is able to edit their account details
    * Path: PUT http://localhost:8080/api/myProfile
    * Reuses GIVEN aMyAccountIsAvailable gets the current logged-in user's object
    * iEditMyProfile updates the logged-in user's details
    * iSeeMyProfileIsUpdated confirms a successful update for the logged-in user's details
    */
   @When("I edit my profile")
   public void iEditMyProfile() throws Exception {
      JSONObject requestBody = new JSONObject();
      requestBody.put("name", "Updated name");
      requestBody.put("email", "Updated email");
      requestBody.put("password", "Updated pw");
      requestBody.put("resume", "Updated resume");
      request.header("Content-Type", "application/json");
      request.header("Authorization", "Bearer", getSecurityKey());
      response = request.body(requestBody.toString()).put(BASE_URL + port + "/api/myProfile");
   }

   @Then("I see my profile is updated")
   public void iSeeMyProfileIsUpdated() {
      Assert.assertEquals(200, response.getStatusCode());
   }
}
