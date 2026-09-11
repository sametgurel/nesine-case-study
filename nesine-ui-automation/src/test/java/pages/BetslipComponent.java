package pages;

import context.CouponModel;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ElementUtils;
import utils.WaitUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class BetslipComponent {

    private static final Logger log = LoggerFactory.getLogger(BetslipComponent.class);
    private final WebDriver driver;
    private final WaitUtils waitUtils;
    private final ElementUtils elementUtils;

    // Betslip Container Locators
    private final By outerCoupon = By.id("outerCoupon");
    private final By basketCoupon = By.id("basketCoupon");
    private final By miniCouponOuter = By.cssSelector(".c-outer");
    private final By couponLoading = By.id("coupon-loading");

    // Events in Betslip
    private final By betslipEventRows = By.cssSelector("#basketCoupon .couponRow, #basketCoupon [id^='cr'], #basketCoupon [data-cs='EventList'] > div, .couponRow.outer");
    private final By betslipEventNames = By.cssSelector("#basketCoupon a.eventName, #basketCoupon .eventName, #basketCoupon [class*='eventName']");
    private final By betslipEventDates = By.cssSelector("#basketCoupon .eventDate, #basketCoupon [class*='eventDate']");
    private final By betslipEventCountSpan = By.cssSelector("#basketCoupon .numberOfEvents, #miniCouponEventCount");

    // Actions
    private final By clearCouponBtn = By.cssSelector("#basketCoupon .btn-clear, #basketCoupon [title*='Sil'], #basketCoupon [title*='Kaldır'], [onclick*='ClearCoupon'], [onclick*='clearBasket']");
    private final By removeSingleEventBtn = By.cssSelector("#basketCoupon .eventCancelBtn, #basketCoupon .ni-times, #basketCoupon .ni-times-circle, #basketCoupon [onclick*='removeevent']");

    public BetslipComponent(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
        this.elementUtils = new ElementUtils(driver);
    }

    public boolean isBetslipDisplayed() {
        waitForBetslipReady();
        try {
            waitUtils.waitForPresence(betslipEventRows);
            return true;
        } catch (Exception e) {
            log.debug("Event rows not immediately present, checking containers: {}", e.getMessage());
        }

        boolean isOuterVisible = waitUtils.isElementVisible(outerCoupon, Duration.ofSeconds(5));
        boolean isBasketVisible = waitUtils.isElementVisible(basketCoupon, Duration.ofSeconds(5));
        boolean isMiniVisible = waitUtils.isElementVisible(miniCouponOuter, Duration.ofSeconds(2));

        return isOuterVisible || isBasketVisible || isMiniVisible;
    }

    public List<String> getBetslipEventNames() {
        waitForBetslipReady();
        try {
            waitUtils.waitForElementsCountGreaterThan(betslipEventNames, 0);
        } catch (Exception e) {
            log.warn("Wait for betslip event names timed out: {}", e.getMessage());
        }

        List<WebElement> nameElements = driver.findElements(betslipEventNames);
        List<String> names = new ArrayList<>();
        for (WebElement el : nameElements) {
            String txt = el.getText().trim();
            if (!txt.isEmpty()) {
                names.add(txt);
            }
        }
        log.info("Found {} event names in betslip: {}", names.size(), names);
        return names;
    }

    public List<String> getBetslipEventDates() {
        waitForBetslipReady();
        try {
            waitUtils.waitForElementsCountGreaterThan(betslipEventDates, 0);
        } catch (Exception e) {
            log.warn("Wait for betslip event dates timed out: {}", e.getMessage());
        }

        List<WebElement> dateElements = driver.findElements(betslipEventDates);
        List<String> dates = new ArrayList<>();
        for (WebElement el : dateElements) {
            String txt = el.getText().trim();
            if (!txt.isEmpty()) {
                dates.add(txt);
            }
        }
        log.info("Found {} event dates in betslip: {}", dates.size(), dates);
        return dates;
    }

    public int getBetslipEventCount() {
        waitForBetslipReady();
        // 1. Check .numberOfEvents badge (e.g. <span class="numberOfEvents">2</span>)
        try {
            List<WebElement> countSpans = driver.findElements(betslipEventCountSpan);
            for (WebElement span : countSpans) {
                String text = span.getText().trim();
                if (!text.isEmpty() && !text.equals("-")) {
                    int count = Integer.parseInt(text);
                    if (count > 0) {
                        return count;
                    }
                }
            }
        } catch (Exception e) {
            log.debug("Could not read event count from badge: {}", e.getMessage());
        }

        // 2. Check event rows
        List<WebElement> rows = driver.findElements(betslipEventRows);
        if (!rows.isEmpty()) {
            return rows.size();
        }

        // 3. Check event names
        return driver.findElements(betslipEventNames).size();
    }

    /**
     * Explicitly wait for betslip to restore events after page navigation (async IndexedDB/storage reload).
     */
    public boolean isBetslipPreserved() {
        waitForBetslipReady();
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> {
                int count = getBetslipEventCount();
                log.debug("Polling betslip event count: {}", count);
                return count > 0;
            });
        } catch (TimeoutException e) {
            log.warn("Betslip was not restored within timeout. Final count: {}", getBetslipEventCount());
            return false;
        }
    }

    public void clearBetslip() {
        log.info("Clearing betslip");
        waitForBetslipReady();

        List<WebElement> clearBtns = driver.findElements(clearCouponBtn);
        if (!clearBtns.isEmpty() && clearBtns.get(0).isDisplayed()) {
            elementUtils.click(clearBtns.get(0));
        } else {
            List<WebElement> removeBtns = driver.findElements(removeSingleEventBtn);
            int safetyLimit = 15;
            while (!removeBtns.isEmpty() && safetyLimit > 0) {
                try {
                    elementUtils.click(removeBtns.get(0));
                    Thread.sleep(500);
                } catch (Exception ignored) {}
                removeBtns = driver.findElements(removeSingleEventBtn);
                safetyLimit--;
            }
        }

        By confirmOk = By.cssSelector("#mod-confirmation-ok, #mod-confirmation [data-id='btnOk'], .modal.in .btn-primary");
        if (waitUtils.isElementPresent(confirmOk, Duration.ofSeconds(2))) {
            elementUtils.clickWithJs(confirmOk);
        }
    }

    public boolean isBetslipEmpty() {
        waitForBetslipReady();
        int count = getBetslipEventCount();
        By nullCoupon = By.cssSelector("#basketCoupon .nullCoupon, .nullCouponText");
        boolean hasNullCouponMsg = waitUtils.isElementVisible(nullCoupon, Duration.ofSeconds(2));
        return count == 0 || hasNullCouponMsg;
    }

    private void waitForBetslipReady() {
        try {
            waitUtils.waitForInvisibility(couponLoading);
        } catch (Exception ignored) {}
    }
}
