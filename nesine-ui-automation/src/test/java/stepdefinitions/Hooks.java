package stepdefinitions;

import driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks {

    private static final Logger log = LoggerFactory.getLogger(Hooks.class);

    @Before
    public void setUp(Scenario scenario) {
        log.info("==================================================================");
        log.info("Starting Scenario: '{}' [Tag(s): {}]", scenario.getName(), scenario.getSourceTagNames());
        log.info("==================================================================");
        // Eagerly initialize driver for this thread
        DriverFactory.getDriver();
    }

    @After
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverFactory.getDriver();
        if (scenario.isFailed() && driver != null) {
            try {
                log.error("Scenario FAILED: '{}'. Capturing screenshot for Allure Report.", scenario.getName());
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Failed_Screenshot_" + scenario.getName().replace(" ", "_"));
            } catch (Exception e) {
                log.error("Failed to capture screenshot: {}", e.getMessage());
            }
        } else {
            log.info("Scenario PASSED: '{}'", scenario.getName());
        }

        // Teardown WebDriver and clear ThreadLocal
        DriverFactory.quitDriver();
        log.info("Browser session closed and ThreadLocal cleared.");
    }
}
