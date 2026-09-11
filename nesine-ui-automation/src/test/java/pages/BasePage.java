package pages;

import config.ConfigManager;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ElementUtils;
import utils.WaitUtils;

import java.time.Duration;
import java.util.List;

public abstract class BasePage {

    protected final Logger log = LoggerFactory.getLogger(getClass());
    protected final WebDriver driver;
    protected final WaitUtils waitUtils;
    protected final ElementUtils elementUtils;

    // Header Links Locators
    protected final By popularCouponsHeaderBtn = By.cssSelector("a[href*='populer-kuponlar'], a[href*='PopulerKuponlar'], a[data-cs*='Populer']");
    protected final By iddaaHeaderLink = By.cssSelector("header a[href*='/iddaa'], #nsn-sports-bar a[href*='/iddaa']");
    protected final By liveScoresHeaderLink = By.cssSelector("header a[href*='/canli-skor'], #nsn-sports-bar a[href*='/canli-skor']");
    protected final By minuteBetHeaderLink = By.cssSelector("header a[href*='/dakika-bahis'], #nsn-sports-bar a[href*='/dakika-bahis']");

    // Modal & Popups Locators
    protected final By cookieAcceptBtn = By.cssSelector("#onetrust-accept-btn-handler, .cookie-accept, button[id*='accept'], a[id*='accept']");
    protected final By genericModalCloseBtn = By.cssSelector(".modal.in .btn-close, .modal.show .btn-close, #mod-base .btn-close, #mod-alert .btn-close");
    protected final By allowContractModalClose = By.cssSelector("#allowContratModal .btn-close, #allowContratModal [data-dismiss='modal']");

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
        this.elementUtils = new ElementUtils(driver);
    }

    public void navigateTo(String pathOrUrl) {
        String targetUrl;
        if (pathOrUrl.startsWith("http://") || pathOrUrl.startsWith("https://")) {
            targetUrl = pathOrUrl;
        } else {
            String baseUrl = ConfigManager.getBaseUrl();
            if (baseUrl.endsWith("/") && pathOrUrl.startsWith("/")) {
                targetUrl = baseUrl + pathOrUrl.substring(1);
            } else if (!baseUrl.endsWith("/") && !pathOrUrl.startsWith("/")) {
                targetUrl = baseUrl + "/" + pathOrUrl;
            } else {
                targetUrl = baseUrl + pathOrUrl;
            }
        }
        log.info("Navigating to URL: {}", targetUrl);
        driver.get(targetUrl);
        waitUtils.waitForPageLoad();
        closeAnyOverlayIfPresent();
    }

    public void closeAnyOverlayIfPresent() {
        try {
            if (waitUtils.isElementPresent(cookieAcceptBtn, Duration.ofSeconds(2))) {
                log.info("Cookie accept banner found. Clicking accept.");
                elementUtils.clickWithJs(cookieAcceptBtn);
            }

            if (waitUtils.isElementPresent(allowContractModalClose, Duration.ofSeconds(1))) {
                log.info("Contract modal found. Closing.");
                elementUtils.clickWithJs(allowContractModalClose);
            }

            List<WebElement> closeBtns = driver.findElements(genericModalCloseBtn);
            for (WebElement btn : closeBtns) {
                if (btn.isDisplayed()) {
                    log.info("Closing open modal dialog.");
                    elementUtils.clickWithJs(btn);
                }
            }
        } catch (Exception e) {
            log.debug("No blocking overlays to close: {}", e.getMessage());
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public void navigateToIddaaPage() {
        log.info("Navigating to İddaa page via header link");
        closeAnyOverlayIfPresent();
        try {
            elementUtils.click(iddaaHeaderLink);
        } catch (Exception e) {
            log.warn("Header click failed, navigating directly to /iddaa: {}", e.getMessage());
            navigateTo(ConfigManager.getIddaaPath());
        }
        waitUtils.waitForPageLoad();
        waitUtils.waitForUrlContains(ConfigManager.getIddaaPath());
        closeAnyOverlayIfPresent();
    }

    public void navigateToLiveScoresPage() {
        log.info("Navigating to Canlı Sonuçlar page via header link");
        closeAnyOverlayIfPresent();
        try {
            elementUtils.click(liveScoresHeaderLink);
        } catch (Exception e) {
            log.warn("Header click failed, navigating directly to /iddaa/canli-skor/futbol: {}", e.getMessage());
            navigateTo(ConfigManager.getLiveScoresPath());
        }
        waitUtils.waitForPageLoad();
        waitUtils.waitForUrlContains("canli-skor");
        closeAnyOverlayIfPresent();
    }

    public void navigateToMinuteBetPage() {
        log.info("Navigating to Dakika Bahis page via header link");
        closeAnyOverlayIfPresent();
        try {
            elementUtils.click(minuteBetHeaderLink);
        } catch (Exception e) {
            log.warn("Header click failed, navigating directly to /iddaa/dakika-bahis: {}", e.getMessage());
            navigateTo(ConfigManager.getMinuteBetPath());
        }
        waitUtils.waitForPageLoad();
        waitUtils.waitForUrlContains("dakika-bahis");
        closeAnyOverlayIfPresent();
    }
}
