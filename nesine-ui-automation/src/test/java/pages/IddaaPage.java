package pages;

import config.ConfigManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class IddaaPage extends BasePage {

    private final By bulletinContainer = By.cssSelector("#bulten, .bulletin-container, .react-bet-list, [data-cs*='Bulten']");

    public IddaaPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAtIddaaPage() {
        waitUtils.waitForPageLoad();
        boolean urlMatches = waitUtils.waitForUrlContains(ConfigManager.getIddaaPath());
        return urlMatches || driver.getCurrentUrl().contains("/iddaa");
    }
}
