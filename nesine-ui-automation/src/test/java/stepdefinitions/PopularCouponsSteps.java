package stepdefinitions;

import context.TestContext;
import driver.DriverFactory;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.HomePage;
import pages.PopularCouponsPage;

public class PopularCouponsSteps {

    private static final Logger log = LoggerFactory.getLogger(PopularCouponsSteps.class);
    private final WebDriver driver;
    private final TestContext testContext;
    private final HomePage homePage;
    private final PopularCouponsPage popularCouponsPage;

    // Injected by PicoContainer
    public PopularCouponsSteps(TestContext testContext) {
        this.testContext = testContext;
        this.driver = DriverFactory.getDriver();
        this.homePage = new HomePage(driver);
        this.popularCouponsPage = new PopularCouponsPage(driver);
    }

    @When("the user clicks the Popüler Kuponlar button")
    public void theUserClicksThePopulerKuponlarButton() {
        log.info("Step: User clicks 'Popüler Kuponlar' button");
        homePage.clickPopularCouponsButton();
    }

    @Then("the Popüler Kuponlar page should be displayed")
    public void thePopulerKuponlarPageShouldBeDisplayed() {
        log.info("Step: Verifying Popular Coupons page is displayed");
        Assertions.assertTrue(popularCouponsPage.isAtPopularCouponsPage(),
                "User should be on Popular Coupons page! Current URL: " + driver.getCurrentUrl());
    }

    @When("the user selects any coupon from the list and clicks Hemen Oyna to add to betslip")
    public void theUserSelectsAnyCouponFromTheListAndClicksHemenOynaToAddToBetslip() {
        log.info("Step: Selecting first available coupon and clicking 'Hemen Oyna'");
        popularCouponsPage.selectFirstCouponAndClickHemenOyna(testContext);
    }

    @When("the user selects coupon at index {int} and clicks Hemen Oyna")
    public void theUserSelectsCouponAtIndexAndClicksHemenOyna(int index) {
        log.info("Step: Selecting coupon at index {} and clicking 'Hemen Oyna'", index);
        popularCouponsPage.selectCouponAndClickHemenOyna(index, testContext);
    }

    @When("the user filters coupons by {string}")
    public void theUserFiltersCouponsBy(String filterOrTabName) {
        log.info("Step: Filtering coupons by '{}'", filterOrTabName);
        popularCouponsPage.selectSportFilterOrTab(filterOrTabName);
    }

    @Then("the popular coupon list should be visible and not empty")
    public void thePopularCouponListShouldBeVisibleAndNotEmpty() {
        log.info("Step: Verifying popular coupon list has items");
        Assertions.assertTrue(popularCouponsPage.hasCouponsListed(),
                "Popular coupons list should contain at least one coupon!");
    }
}
