package stepdefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.junit.Assert;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class SauceDemoSteps {

    WebDriver driver;

    @Given("the user launches the SauceDemo website")
    public void launchWebsite() {

        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);

        options.setExperimentalOption("prefs", prefs);

        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-notifications");
        options.addArguments("--incognito");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-features=PasswordLeakDetection");

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://www.saucedemo.com");
    }

    @And("the user logs in with {string} and {string}")
    public void login(String username, String password) {

        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();
    }

    @When("the user adds {string} and {string} to the cart")
    public void addProducts(String item1, String item2) {

        String xpath1 = "//div[text()='" + item1 + "']/ancestor::div[@class='inventory_item']//button";
        String xpath2 = "//div[text()='" + item2 + "']/ancestor::div[@class='inventory_item']//button";

        driver.findElement(By.xpath(xpath1)).click();
        driver.findElement(By.xpath(xpath2)).click();
    }

    @And("the user navigates to the cart page")
    public void navigateToCart() {
        driver.findElement(By.className("shopping_cart_link")).click();
    }

    @And("the user removes {string} from the cart")
    public void removeItem(String itemName) {

        String removeXpath = "//div[text()='" + itemName + "']/ancestor::div[@class='cart_item']//button";
        driver.findElement(By.xpath(removeXpath)).click();
    }

    @Then("the cart should show {int} item")
    public void verifyCartCount(int expectedCount) {

        By cartBadge = By.className("shopping_cart_badge");

        if (driver.findElements(cartBadge).size() > 0) {
            String count = driver.findElement(cartBadge).getText();
            Assert.assertEquals(String.valueOf(expectedCount), count);
        } else {
            Assert.assertEquals(0, expectedCount);
        }
    }

    @When("the user proceeds to checkout")
    public void proceedToCheckout() {
        driver.findElement(By.id("checkout")).click();
    }

    @And("the user enters first name {string}, last name {string}, and zip {string}")
    public void enterDetails(String fname, String lname, String zip) {

        driver.findElement(By.id("first-name")).sendKeys(fname);
        driver.findElement(By.id("last-name")).sendKeys(lname);
        driver.findElement(By.id("postal-code")).sendKeys(zip);
        driver.findElement(By.id("continue")).click();
    }

    @And("the user clicks the Finish button")
    public void clickFinish() {
        driver.findElement(By.id("finish")).click();
    }

    @Then("an {string} confirmation message should be displayed")
    public void verifyConfirmation(String expectedMsg) {

        String actualMsg = driver.findElement(By.className("complete-header")).getText();
        Assert.assertEquals(expectedMsg, actualMsg);

        driver.quit();
    }
}
