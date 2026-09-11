package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElementUtils {

    private static final Logger log = LoggerFactory.getLogger(ElementUtils.class);
    private final WebDriver driver;
    private final WaitUtils waitUtils;

    public ElementUtils(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    public void click(By locator) {
        WebElement element = waitUtils.waitForClickability(locator);
        scrollIntoView(element);
        try {
            element.click();
        } catch (ElementClickInterceptedException | StaleElementReferenceException e) {
            log.warn("Standard click intercepted for locator: {}. Retrying via JavaScript click.", locator);
            clickWithJs(element);
        }
    }

    public void click(WebElement element) {
        waitUtils.waitForClickability(element);
        scrollIntoView(element);
        try {
            element.click();
        } catch (ElementClickInterceptedException | StaleElementReferenceException e) {
            log.warn("Standard click intercepted on element. Retrying via JavaScript click.");
            clickWithJs(element);
        }
    }

    public void clickWithJs(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void clickWithJs(By locator) {
        WebElement element = waitUtils.waitForPresence(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element);
    }

    public void scrollIntoView(By locator) {
        WebElement element = waitUtils.waitForPresence(locator);
        scrollIntoView(element);
    }

    public String getText(By locator) {
        WebElement element = waitUtils.waitForVisibility(locator);
        return element.getText().trim();
    }

    public String getText(WebElement element) {
        waitUtils.waitForVisibility(element);
        return element.getText().trim();
    }

    public void moveToElement(WebElement element) {
        new Actions(driver).moveToElement(element).perform();
    }
}
