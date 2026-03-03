package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/feature/Ecommerce.feature",
        glue = "stepdefinitions",
        plugin = {"pretty","html:target/report.html"},
        monochrome = true
)

public class EcommerceTest {
}