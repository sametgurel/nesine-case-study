package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LiveScoresPage extends BasePage {

    private final By liveScoresContainer = By.cssSelector(".live-score-container, #live-scores, [data-cs*='LiveScore']");

    public LiveScoresPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAtLiveScoresPage() {
        waitUtils.waitForPageLoad();
        boolean urlMatches = waitUtils.waitForUrlContains("canli-skor");
        return urlMatches || driver.getCurrentUrl().contains("canli-skor");
    }
}
