package stepdefinitions;

import java.time.Duration;
import java.util.Arrays;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.cucumber.java.en.*;

public class BlazeSteps {

    WebDriver driver;
    int rowCount;
    double minPrice;

    @Given("User opens BlazeDemo website")
    public void openWebsite() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://blazedemo.com/");
    }

    @When("User selects departure city")
    public void selectDepartureCity() {
        WebElement fromDropdown = driver.findElement(By.name("fromPort"));
        Select fromSelect = new Select(fromDropdown);
        fromSelect.selectByIndex(3);
    }

    @When("User selects destination city")
    public void selectDestinationCity() {
        WebElement toDropdown = driver.findElement(By.name("toPort"));
        Select toSelect = new Select(toDropdown);
        toSelect.selectByVisibleText("New York");
    }

    @When("User clicks Find Flights")
    public void clickFindFlights() {

        driver.findElement(By.xpath("//input[@value='Find Flights']")).click();
    }

    @Then("Available flights should be displayed")
    public void verifyFlightsDisplayed() {
        rowCount = driver.findElements(
                By.xpath("//table[@class='table']//tbody/tr")
        ).size();
    }

    @When("User selects lowest priced flight")
    public void selectLowestPriceFlight() throws InterruptedException {

        double prices[] = new double[rowCount];

        for (int i = 1; i <= rowCount; i++) {
            String priceText = driver.findElement(
                    By.xpath("//table[@class='table']//tbody/tr[" + i + "]/td[6]")
            ).getText();

            prices[i - 1] = Double.parseDouble(priceText.substring(1));
        }

        Arrays.sort(prices);
        minPrice = prices[0];

        for (int i = 1; i <= rowCount; i++) {
            String priceText = driver.findElement(
                    By.xpath("//table[@class='table']//tbody/tr[" + i + "]/td[6]")
            ).getText();

            double price = Double.parseDouble(priceText.substring(1));
          Thread.sleep(2000);
            if (price == minPrice) {
                driver.findElement(
                        By.xpath("//table[@class='table']//tbody/tr[" + i + "]/td[1]")
                ).click();
                break;
            }
        }
    }

    @When("User enters passenger details")
    public void enterPassengerDetails() {
        driver.findElement(By.id("inputName")).sendKeys("Raghu Vamsi");
        driver.findElement(By.id("address")).sendKeys("White and Green Building");
        driver.findElement(By.id("city")).sendKeys("Hyderabad");
        driver.findElement(By.id("state")).sendKeys("Telangana");
        driver.findElement(By.id("zipCode")).sendKeys("500001");
        driver.findElement(By.id("rememberMe")).click();
    }

    @When("User clicks Purchase Flight")
    public void clickPurchaseFlight() {
        driver.findElement(By.xpath("//input[@value='Purchase Flight']")).click();
    }

    @Then("Booking confirmation page should be displayed")
    public void verifyConfirmationPage() throws InterruptedException {
        String confirmationText = driver.findElement(By.tagName("h1")).getText();
        System.out.println("Confirmation Message: " + confirmationText);
        Thread.sleep(2000);
        driver.quit();
    }
}