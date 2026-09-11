package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MinuteBetPage extends BasePage {

    private final By minuteBetContainer = By.cssSelector(".minute-bet-container, #minute-bet, [data-cs*='MinuteBet']");

    public MinuteBetPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAtMinuteBetPage() {
        waitUtils.waitForPageLoad();
        boolean urlMatches = waitUtils.waitForUrlContains("dakika-bahis");
        return urlMatches || driver.getCurrentUrl().contains("dakika-bahis");
    }
}
