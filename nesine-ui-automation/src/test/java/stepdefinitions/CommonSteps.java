package stepdefinitions;

import context.TestContext;
import driver.DriverFactory;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.*;

import java.util.List;

public class CommonSteps {

    private static final Logger log = LoggerFactory.getLogger(CommonSteps.class);
    private final WebDriver driver;
    private final TestContext testContext;
    private final HomePage homePage;
    private final IddaaPage iddaaPage;
    private final LiveScoresPage liveScoresPage;
    private final MinuteBetPage minuteBetPage;
    private final BetslipComponent betslipComponent;

    // Injected by PicoContainer
    public CommonSteps(TestContext testContext) {
        this.testContext = testContext;
        this.driver = DriverFactory.getDriver();
        this.homePage = new HomePage(driver);
        this.iddaaPage = new IddaaPage(driver);
        this.liveScoresPage = new LiveScoresPage(driver);
        this.minuteBetPage = new MinuteBetPage(driver);
        this.betslipComponent = new BetslipComponent(driver);
    }

    @Given("the user is on the Nesine homepage")
    public void theUserIsOnTheNesineHomepage() {
        log.info("Step: Navigating to Nesine Homepage");
        homePage.openHomePage();
        Assertions.assertTrue(homePage.isAtHomePage(), "User should be on Nesine homepage");
    }

    @When("the user navigates to {string} page via header")
    public void theUserNavigatesToPageViaHeader(String pageName) {
        log.info("Step: Navigating to '{}' page via header", pageName);
        switch (pageName.toLowerCase().trim()) {
            case "iddaa":
            case "i̇ddaa":
                homePage.navigateToIddaaPage();
                break;
            case "canli sonuclar":
            case "canlı sonuçlar":
            case "canli-skor":
                homePage.navigateToLiveScoresPage();
                break;
            case "dakika bahis":
            case "dakika-bahis":
                homePage.navigateToMinuteBetPage();
                break;
            default:
                throw new IllegalArgumentException("Unknown page name in header navigation: " + pageName);
        }
    }

    @Then("the {string} page should be opened correctly")
    public void thePageShouldBeOpenedCorrectly(String pageName) {
        log.info("Step: Verifying '{}' page is opened", pageName);
        boolean isOpened;
        switch (pageName.toLowerCase().trim()) {
            case "iddaa":
            case "i̇ddaa":
                isOpened = iddaaPage.isAtIddaaPage();
                break;
            case "canli sonuclar":
            case "canlı sonuçlar":
            case "canli-skor":
                isOpened = liveScoresPage.isAtLiveScoresPage();
                break;
            case "dakika bahis":
            case "dakika-bahis":
                isOpened = minuteBetPage.isAtMinuteBetPage();
                break;
            default:
                throw new IllegalArgumentException("Unknown page name: " + pageName);
        }
        Assertions.assertTrue(isOpened, "Expected page '" + pageName + "' was not opened! Current URL: " + driver.getCurrentUrl());
    }

    @Then("the user navigates through header pages sequentially verifying each page opens and the betslip is preserved:")
    public void theUserNavigatesThroughHeaderPagesSequentiallyVerifyingEachPageOpensAndTheBetslipIsPreserved(DataTable dataTable) {
        List<String> pages = dataTable.asList();
        for (String targetPage : pages) {
            log.info("Sequential Navigation Step: Navigating to '{}'", targetPage);
            theUserNavigatesToPageViaHeader(targetPage);
            thePageShouldBeOpenedCorrectly(targetPage);

            log.info("Verifying betslip is preserved on '{}'", targetPage);
            Assertions.assertTrue(betslipComponent.isBetslipPreserved(),
                    "Betslip was NOT preserved after navigating to '" + targetPage + "'! Current URL: " + driver.getCurrentUrl());
        }
    }
}
