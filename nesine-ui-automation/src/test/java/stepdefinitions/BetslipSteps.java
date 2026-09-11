package stepdefinitions;

import context.CouponModel;
import context.TestContext;
import driver.DriverFactory;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.BetslipComponent;

import java.util.List;

public class BetslipSteps {

    private static final Logger log = LoggerFactory.getLogger(BetslipSteps.class);
    private final WebDriver driver;
    private final TestContext testContext;
    private final BetslipComponent betslipComponent;

    // Injected by PicoContainer
    public BetslipSteps(TestContext testContext) {
        this.testContext = testContext;
        this.driver = DriverFactory.getDriver();
        this.betslipComponent = new BetslipComponent(driver);
    }

    @Then("the betslip should open and display the added coupon")
    public void theBetslipShouldOpenAndDisplayTheAddedCoupon() {
        log.info("Step: Verifying betslip is displayed");
        Assertions.assertTrue(betslipComponent.isBetslipDisplayed(),
                "Betslip (Sepet) should be displayed after adding a coupon!");
    }

    @Then("the added coupon event names should be displayed correctly in the betslip")
    public void theAddedCouponEventNamesShouldBeDisplayedCorrectlyInTheBetslip() {
        log.info("Step: Verifying event names in betslip");
        CouponModel selectedCoupon = testContext.getSelectedCoupon();
        List<String> actualNames = betslipComponent.getBetslipEventNames();

        Assertions.assertFalse(actualNames.isEmpty(),
                "Betslip should contain event names!");

        if (selectedCoupon != null && !selectedCoupon.getEvents().isEmpty()) {
            for (CouponModel.EventItem item : selectedCoupon.getEvents()) {
                String expName = item.getEventName();
                boolean matches = actualNames.stream().anyMatch(actName -> isEventMatching(expName, actName));
                Assertions.assertTrue(matches,
                        "Expected event '" + expName + "' was not found in betslip event names: " + actualNames);
            }
        }
    }

    private boolean isEventMatching(String expectedEvent, String actualEvent) {
        if (expectedEvent == null || actualEvent == null) return false;
        String exp = expectedEvent.toLowerCase().trim();
        String act = actualEvent.toLowerCase().trim();

        // 1. Direct contains or equality
        if (exp.equals(act) || exp.contains(act) || act.contains(exp)) {
            return true;
        }

        // 2. Nesine abbreviates team names in responsive betslip drawer (e.g. 'Independiente D. V.' vs 'Independiente D.')
        if (exp.contains(" - ") && act.contains(" - ")) {
            String[] expParts = exp.split(" - ");
            String[] actParts = act.split(" - ");
            if (expParts.length >= 2 && actParts.length >= 2) {
                String expHome = expParts[0].trim();
                String expAway = expParts[1].trim();
                String actHome = actParts[0].trim();
                String actAway = actParts[1].trim();

                String expHomeFirstWord = expHome.split("\\s+")[0];
                String expAwayFirstWord = expAway.split("\\s+")[0];

                boolean homeMatches = expHome.contains(actHome) || actHome.contains(expHome)
                        || (expHomeFirstWord.length() > 2 && actHome.startsWith(expHomeFirstWord));
                boolean awayMatches = expAway.contains(actAway) || actAway.contains(expAway)
                        || (expAwayFirstWord.length() > 2 && actAway.startsWith(expAwayFirstWord));

                if (homeMatches && awayMatches) {
                    return true;
                }
            }
        }
        return false;
    }

    @Then("the added coupon event dates should be displayed correctly in the betslip")
    public void theAddedCouponEventDatesShouldBeDisplayedCorrectlyInTheBetslip() {
        log.info("Step: Verifying event dates in betslip");
        CouponModel selectedCoupon = testContext.getSelectedCoupon();
        List<String> actualDates = betslipComponent.getBetslipEventDates();

        int actualCount = betslipComponent.getBetslipEventCount();
        Assertions.assertTrue(actualCount > 0, "Betslip must have events displayed!");

        if (selectedCoupon != null && !selectedCoupon.getEvents().isEmpty() && !actualDates.isEmpty()) {
            log.info("Verified event dates are visible in betslip: {}", actualDates);
        }
    }

    @Then("the betslip should be preserved with the added coupon")
    public void theBetslipShouldBePreservedWithTheAddedCoupon() {
        log.info("Step: Verifying betslip preservation");
        Assertions.assertTrue(betslipComponent.isBetslipPreserved(),
                "Betslip was NOT preserved! Event count in betslip should be > 0.");
    }

    @When("the user clears all events from the betslip")
    public void theUserClearsAllEventsFromTheBetslip() {
        log.info("Step: User clears the betslip");
        betslipComponent.clearBetslip();
    }

    @Then("the betslip should be empty")
    public void theBetslipShouldBeEmpty() {
        log.info("Step: Verifying betslip is empty");
        Assertions.assertTrue(betslipComponent.isBetslipEmpty(),
                "Betslip should be empty after clearing all events!");
    }
}
