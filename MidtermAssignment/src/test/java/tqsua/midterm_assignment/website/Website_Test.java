package tqsua.midterm_assignment.website;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import tqsua.midterm_assignment.MidtermAssignmentApplication;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = MidtermAssignmentApplication.class)
@AutoConfigureMockMvc
public class Website_Test {
  private WebDriver webDriver;

  @When("I navigate to {string}")
  public void iNavigateTo(String url) {
    WebDriverManager.firefoxdriver().setup();
    webDriver = new FirefoxDriver();
    webDriver.get(url);
  }

  @And("I select the country {string}")
  public void iSelectTheCountry(String country) {
    WebDriverWait wait = new WebDriverWait(webDriver, 60);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("country")));
    webDriver.findElement(By.id("country")).click();
    webDriver.findElement(By.id("country")).findElement(By.xpath("//option[. = '"+ country +"']")).click();
  }

  @And("I select the state {string}")
  public void iSelectTheState(String state) {
    WebDriverWait wait = new WebDriverWait(webDriver, 60);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("state")));
    webDriver.findElement(By.id("state")).click();
    webDriver.findElement(By.id("state")).findElement(By.xpath("//option[. = '" + state +"']")).click();
  }

  @And("I select the city {string}")
  public void iSelectTheCity(String city) {
      WebDriverWait wait = new WebDriverWait(webDriver, 60);
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("city")));
      webDriver.findElement(By.id("city")).click();
      webDriver.findElement(By.id("city")).findElement(By.xpath("//option[. = '" + city + "']")).click();
  }

  @And("I click the button to get the air quality")
  public void iClickAirQuality() {
      webDriver.findElement(By.id("aqbutton")).click();
  }

  @Then("I should be shown the {string}")
  public void iShouldBeShown(String what) {
      String id = "p_" + what.toLowerCase().replace(' ', '_');
      WebDriverWait wait = new WebDriverWait(webDriver, 60);
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
      assertThat(webDriver.findElement(By.id(id)).getText(), startsWith(what));
  }

  @And("I click the button to get the statistics")
  public void iClickTheStatisticsButton() {
    webDriver.findElement(By.id("statsbutton")).click();
  }

  @And("I should be shown the API {string}")
  public void iShouldBeShowTheAPIStats(String what) {
    assertThat(webDriver.findElement(By.id("p_"+what.toLowerCase())).getText(), startsWith(what));
  }
}