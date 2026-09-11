package pages;

import config.ConfigManager;
import context.CouponModel;
import context.TestContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class PopularCouponsPage extends BasePage {

    // Locators for Popular Coupons Cards & Content
    private final By couponContainer = By.cssSelector("#nsn-popularCoupons, .popular-coupons-container, [data-cs*='PopularCoupons']");
    private final By couponCards = By.cssSelector(".new-iddaa-coupon-detail, [class*='new-iddaa-coupon'], .popular-coupon-card, .coupon-body");
    
    // Crucial: Exclude tabItem button so we click ONLY coupon card "Hemen Oyna" buttons
    private final By cardHemenOynaButtons = By.xpath("//button[contains(., 'Hemen Oyna') and not(contains(@class, 'tabItem'))]");

    // Locators for Events inside a Coupon Card
    private final By eventRows = By.cssSelector("[data-testid^='event-row'], .eventRow, .couponRow");
    private final By eventNames = By.cssSelector("[data-testid='event-name'], .event-name, .eventName, a.eventName");
    private final By eventDates = By.cssSelector("[data-testid*='date'], .eventDate, .date, [class*='date']");

    // Locators for Filter tabs (Hemen Oyna, Kazananlar)
    private final By popularCouponsTabs = By.cssSelector("button.tabItem, [class*='tabItem'], ul[role='tablist'] li, .nav-tabs li");

    public PopularCouponsPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        navigateTo(ConfigManager.getPopularCouponsPath());
        waitUtils.waitForPageLoad();
    }

    public boolean isAtPopularCouponsPage() {
        return waitUtils.waitForUrlContains("populer-kuponlar");
    }

    public List<WebElement> getCouponList() {
        waitUtils.waitForPresence(couponContainer);
        try {
            waitUtils.waitForElementsCountGreaterThan(cardHemenOynaButtons, 0);
        } catch (Exception e) {
            log.warn("Timed out waiting for card Hemen Oyna buttons count > 0: {}", e.getMessage());
        }
        return driver.findElements(couponCards);
    }

    public void selectFirstCouponAndClickHemenOyna(TestContext testContext) {
        selectCouponAndClickHemenOyna(0, testContext);
    }

    public void selectCouponAndClickHemenOyna(int couponIndex, TestContext testContext) {
        log.info("Selecting popular coupon at index {} and saving event details", couponIndex);
        closeAnyOverlayIfPresent();

        // Wait for card 'Hemen Oyna' buttons to appear
        List<WebElement> playButtons = waitUtils.waitForElementsCountGreaterThan(cardHemenOynaButtons, 0);
        if (playButtons.isEmpty()) {
            throw new RuntimeException("No card 'Hemen Oyna' buttons found on Popular Coupons page!");
        }

        if (couponIndex >= playButtons.size()) {
            couponIndex = 0;
        }

        WebElement targetPlayButton = playButtons.get(couponIndex);
        elementUtils.scrollIntoView(targetPlayButton);

        // Find the coupon card and extract event details
        CouponModel couponModel = new CouponModel();
        extractEventDetailsForCoupon(couponIndex, couponModel);
        testContext.setSelectedCoupon(couponModel);

        log.info("Extracted {} events from popular coupon. Clicking card 'Hemen Oyna'.", couponModel.getEvents().size());

        try {
            targetPlayButton.click();
        } catch (Exception e) {
            log.warn("Standard click on Hemen Oyna failed, retrying with JavaScript click: {}", e.getMessage());
            elementUtils.clickWithJs(targetPlayButton);
        }

        // Wait for betslip to update with events
        try {
            waitUtils.waitForPresence(By.cssSelector("#basketCoupon .couponRow, #basketCoupon .eventName"));
        } catch (Exception e) {
            log.debug("Waiting for betslip events presence: {}", e.getMessage());
        }
    }

    private void extractEventDetailsForCoupon(int couponIndex, CouponModel couponModel) {
        List<WebElement> playButtons = driver.findElements(cardHemenOynaButtons);
        if (playButtons.isEmpty()) return;

        WebElement targetBtn = playButtons.get(couponIndex);
        // Find parent card of this button
        List<WebElement> namesElements = new ArrayList<>();
        List<WebElement> datesElements = new ArrayList<>();

        try {
            WebElement card = (WebElement) ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                    "return arguments[0].closest('.new-iddaa-coupon-detail, [class*=\"coupon\"], [class*=\"card\"]') || arguments[0].parentElement.parentElement.parentElement;", targetBtn);
            if (card != null) {
                namesElements = card.findElements(eventNames);
                datesElements = card.findElements(eventDates);
            }
        } catch (Exception e) {
            log.warn("Could not find closest card for button: {}", e.getMessage());
        }

        if (namesElements.isEmpty()) {
            namesElements = driver.findElements(eventNames);
            datesElements = driver.findElements(eventDates);
        }

        int count = Math.min(namesElements.size(), datesElements.size());
        if (count == 0 && !namesElements.isEmpty()) {
            for (WebElement nameEl : namesElements) {
                String name = nameEl.getText().trim();
                if (!name.isEmpty()) {
                    couponModel.addEvent(name, "", "", "");
                }
            }
        } else {
            for (int i = 0; i < count; i++) {
                String name = namesElements.get(i).getText().trim();
                String date = datesElements.get(i).getText().trim();
                if (!name.isEmpty()) {
                    couponModel.addEvent(name, date, "", "");
                    log.info("Extracted coupon event [{}]: Name='{}' | Date='{}'", i, name, date);
                }
            }
        }
    }

    public boolean hasCouponsListed() {
        // Either card Hemen Oyna buttons or coupon cards are present
        List<WebElement> buttons = driver.findElements(cardHemenOynaButtons);
        if (!buttons.isEmpty()) {
            return true;
        }
        List<WebElement> cards = driver.findElements(couponCards);
        return !cards.isEmpty();
    }

    public void selectSportFilterOrTab(String tabOrFilterName) {
        log.info("Switching to filter or tab: {}", tabOrFilterName);
        closeAnyOverlayIfPresent();

        By specificTabLocator = By.xpath("//button[contains(@class, 'tabItem') and contains(., '" + tabOrFilterName + "')] | //*[contains(@class, 'tab') or contains(@class, 'filter') or self::a or self::li or self::button][contains(normalize-space(), '" + tabOrFilterName + "')]");
        try {
            WebElement tab = waitUtils.waitForClickability(specificTabLocator);
            elementUtils.click(tab);
            waitUtils.waitForPageLoad();
        } catch (Exception e) {
            log.warn("Tab '{}' not clickable directly, attempting JS click on tab list: {}", tabOrFilterName, e.getMessage());
            List<WebElement> tabs = driver.findElements(popularCouponsTabs);
            for (WebElement t : tabs) {
                if (t.getText().trim().equalsIgnoreCase(tabOrFilterName.trim()) || t.getText().contains(tabOrFilterName)) {
                    elementUtils.clickWithJs(t);
                    break;
                }
            }
        }

        // Allow dynamic list update
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}
    }
}
