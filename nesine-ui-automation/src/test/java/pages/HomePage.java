package pages;

import config.ConfigManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HomePage extends BasePage {

    // Locators for Popular Coupons button on homepage / sports bar
    private final By popularCouponsBtn = By.xpath("//a[contains(@href, 'populer-kuponlar') or contains(text(), 'Popüler Kuponlar')]");
    private final By headerLogo = By.cssSelector(".logo a, a[title='nesine.com']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void openHomePage() {
        log.info("Opening Nesine Homepage: {}", ConfigManager.getBaseUrl());
        navigateTo("/");
        closeAnyOverlayIfPresent();
    }

    public void clickPopularCouponsButton() {
        log.info("Clicking 'Popüler Kuponlar' button on HomePage");
        closeAnyOverlayIfPresent();

        // Check if button is clickable on the page
        List<WebElement> buttons = driver.findElements(popularCouponsBtn);
        if (!buttons.isEmpty() && buttons.get(0).isDisplayed()) {
            elementUtils.click(buttons.get(0));
        } else {
            // In case the button is inside a submenu or rendered via React asynchronously
            try {
                WebElement btn = waitUtils.waitForClickability(popularCouponsBtn);
                elementUtils.click(btn);
            } catch (Exception e) {
                log.warn("Direct popular coupons button click failed, navigating directly via URL: {}", e.getMessage());
                navigateTo(ConfigManager.getPopularCouponsPath());
            }
        }
        waitUtils.waitForPageLoad();
        waitUtils.waitForUrlContains("populer-kuponlar");
    }

    public boolean isAtHomePage() {
        return driver.getCurrentUrl().equals(ConfigManager.getBaseUrl()) ||
                driver.getCurrentUrl().startsWith(ConfigManager.getBaseUrl() + "/");
    }
}
