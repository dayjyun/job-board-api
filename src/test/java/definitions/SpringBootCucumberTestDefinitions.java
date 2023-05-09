package definitions;

import io.cucumber.java.en.Given;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.response.Response;
import jobboardapi.JobBoardApiApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = JobBoardApiApplication.class)
public class SpringBootCucumberTestDefinitions {
   private static final String BASE_URL = "http://localhost:";
   private static Response response;

   @LocalServerPort
   String port;

   @Given("A list of books are available")
   public void aListOfBooksAreAvailable() {
   }
}
