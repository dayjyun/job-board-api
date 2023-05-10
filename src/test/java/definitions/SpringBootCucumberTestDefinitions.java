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


@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = JobBoardApiApplication.class)
public class SpringBootCucumberTestDefinitions {
   private static final String BASE_URL = "http://localhost:";
   private static Response response;

   @LocalServerPort
   String port;

    //   Scenario: User is able to view another user's account details
    //   @GetMapping(path = "/{userId}")
    @Given("A user account is available")
    public void aUserAccountIsAvailable() {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        response = request.get(BASE_URL + port + "/api/users/1");
        System.out.println(response.getBody().asString());
    }

    @When("I search for another user's id")
    public void iSearchForAnotherUserSId() {
        Assert.assertNotNull(String.valueOf(response));
    }

    @Then("I can see the user's account details")
    public void iCanSeeTheUserSAccountDetails() {
        Assert.assertEquals(200, response.getStatusCode());
    }

    // Scenario: User is able to see a list of all businesses
    @Given("A list of businesses are available")
    public void aListOfBusinessesAreAvailable() {
    }
}
