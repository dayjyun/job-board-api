package definitions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import jobboardapi.JobBoardApiApplication;
import jobboardapi.models.Business;
import jobboardapi.models.User;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;


@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = JobBoardApiApplication.class)
public class SpringBootCucumberTestDefinitions {

   private static final String BASE_URL = "http://localhost:";
   private static Response response;
   private static ResponseEntity<String> responseEntity;
   private static List<?> list;

   @LocalServerPort
   String port;

     /**
     * Testing for Scenario: User is able to view another user's account details
     * This is the GET request at the endpoint http://localhost:8080/api/users/{userId}
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
     * Testing for Scenario: User is able to see a list of all businesses
     * This is the GET request at the endpoint http://localhost:8080/api/businesses
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
}
