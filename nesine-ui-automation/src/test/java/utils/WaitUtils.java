package utils;

import config.ConfigManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

/**
 * Synchronization and Explicit Wait utility.
 * Thread.sleep() is strictly avoided across the entire framework.
 */
public class WaitUtils {

    private static final Logger log = LoggerFactory.getLogger(WaitUtils.class);
    private final WebDriver driver;
    private final WebDriverWait defaultWait;

    public WaitUtils(WebDriver driver) {
        this.driver = driver;
        this.defaultWait = new WebDriverWait(driver, Duration.ofSeconds(ConfigManager.getExplicitTimeout()));
    }

    public WebElement waitForVisibility(By locator) {
        log.debug("Waiting for visibility of element: {}", locator);
        return defaultWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForVisibility(WebElement element) {
        return defaultWait.until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitForClickability(By locator) {
        log.debug("Waiting for clickability of element: {}", locator);
        return defaultWait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement waitForClickability(WebElement element) {
        return defaultWait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public WebElement waitForPresence(By locator) {
        log.debug("Waiting for presence of element: {}", locator);
        return defaultWait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public List<WebElement> waitForAllVisible(By locator) {
        log.debug("Waiting for all elements to be visible: {}", locator);
        return defaultWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public boolean waitForInvisibility(By locator) {
        log.debug("Waiting for invisibility of element: {}", locator);
        return defaultWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public boolean waitForInvisibility(WebElement element) {
        return defaultWait.until(ExpectedConditions.invisibilityOf(element));
    }

    public boolean waitForUrlContains(String fraction) {
        log.debug("Waiting for URL to contain: {}", fraction);
        return defaultWait.until(ExpectedConditions.urlContains(fraction));
    }

    public boolean waitForPageLoad() {
        ExpectedCondition<Boolean> pageLoadCondition = d -> {
            assert d != null;
            return ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete");
        };
        return defaultWait.until(pageLoadCondition);
    }

    public List<WebElement> waitForElementsCountGreaterThan(By locator, int count) {
        log.debug("Waiting for element count > {} for locator: {}", count, locator);
        return defaultWait.until(d -> {
            List<WebElement> elements = driver.findElements(locator);
            return elements.size() > count ? elements : null;
        });
    }

    public boolean isElementPresent(By locator, Duration timeout) {
        try {
            new WebDriverWait(driver, timeout).until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    public boolean isElementVisible(By locator, Duration timeout) {
        try {
            new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }
}
