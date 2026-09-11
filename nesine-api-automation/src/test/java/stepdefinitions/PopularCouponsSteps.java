package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import pojos.CouponEvent;
import pojos.ErrorItem;
import pojos.EventOutcome;
import pojos.PopularCoupon;
import pojos.PopularCouponsResponse;
import utils.ContextKey;
import utils.ScenarioContext;
import utils.SpecBuilder;
import utils.SportTypeMapper;

import java.util.List;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PopularCouponsSteps {

    private static final Pattern MD5_HEX_PATTERN = Pattern.compile("^[a-fA-F0-9]{32}$");

    @Given("the Nesine API specification is initialized")
    public void theNesineApiSpecificationIsInitialized() {
        // Base URI, timeout, headers and Allure filters configured in SpecBuilder
        Assertions.assertNotNull(SpecBuilder.getRequestSpec(), "Request specification should not be null");
    }

    @When("the client sends a GET request to {string} with query parameter {string} as {string}")
    public void theClientSendsAGetRequestToWithQueryParameterAs(String endpoint, String paramName, String paramValue) {
        ScenarioContext.set(ContextKey.ENDPOINT, endpoint);

        long startTime = System.currentTimeMillis();
        Response response = given()
                .spec(SpecBuilder.getRequestSpec())
                .queryParam(paramName, paramValue)
                .when()
                .get(endpoint);
        long duration = System.currentTimeMillis() - startTime;

        ScenarioContext.set(ContextKey.RESPONSE, response);
        ScenarioContext.set(ContextKey.RESPONSE_TIME_MS, duration);
        ScenarioContext.set(ContextKey.STATUS_CODE, response.getStatusCode());
    }

    @When("the client sends a GET request to {string} without any query parameters")
    public void theClientSendsAGetRequestToWithoutAnyQueryParameters(String endpoint) {
        ScenarioContext.set(ContextKey.ENDPOINT, endpoint);

        long startTime = System.currentTimeMillis();
        Response response = given()
                .spec(SpecBuilder.getRequestSpec())
                .when()
                .get(endpoint);
        long duration = System.currentTimeMillis() - startTime;

        ScenarioContext.set(ContextKey.RESPONSE, response);
        ScenarioContext.set(ContextKey.RESPONSE_TIME_MS, duration);
        ScenarioContext.set(ContextKey.STATUS_CODE, response.getStatusCode());
    }

    @Then("the HTTP status code should be {int}")
    public void theHttpStatusCodeShouldBe(int expectedStatusCode) {
        Response response = getResponse();
        Assertions.assertEquals(expectedStatusCode, response.getStatusCode(),
                "HTTP Status code mismatch! Expected: " + expectedStatusCode + " but got: " + response.getStatusCode());
    }

    @Then("the response body field {string} should be {int}")
    public void theResponseBodyFieldShouldBe(String fieldName, int expectedValue) {
        Response response = getResponse();
        int actualValue = response.jsonPath().getInt(fieldName);
        Assertions.assertEquals(expectedValue, actualValue,
                "Field '" + fieldName + "' mismatch! Expected: " + expectedValue + " but got: " + actualValue);
    }

    @Then("the popular coupon list {string} should not be empty")
    public void thePopularCouponListShouldNotBeEmpty(String listFieldName) {
        PopularCouponsResponse responsePojo = getOrDeserializeResponse();
        Assertions.assertNotNull(responsePojo.getD(), "Coupon list '" + listFieldName + "' should not be null");
        Assertions.assertFalse(responsePojo.getD().isEmpty(), "Coupon list '" + listFieldName + "' should not be empty");
    }

    @Then("each coupon eventCount value must strictly equal the number of items in its events list")
    public void eachCouponEventCountValueMustStrictlyEqualTheNumberOfItemsInItsEventsList() {
        PopularCouponsResponse responsePojo = getOrDeserializeResponse();
        List<PopularCoupon> coupons = responsePojo.getD();
        Assertions.assertNotNull(coupons, "Coupon list must not be null");

        for (int i = 0; i < coupons.size(); i++) {
            PopularCoupon coupon = coupons.get(i);
            int declaredEventCount = coupon.getEventCount();
            int actualEventsSize = coupon.getEvents() != null ? coupon.getEvents().size() : 0;

            Assertions.assertEquals(declaredEventCount, actualEventsSize,
                    "Coupon at index " + i + " (Hash: " + coupon.getCouponHash() +
                            ") declared eventCount (" + declaredEventCount +
                            ") does not match actual events list size (" + actualEventsSize + ")");
        }
    }

    @Then("for all events across all coupons minOdd must be less than or equal to maxOdd")
    public void forAllEventsAcrossAllCouponsMinOddMustBeLessThanOrEqualToMaxOdd() {
        PopularCouponsResponse responsePojo = getOrDeserializeResponse();
        List<PopularCoupon> coupons = responsePojo.getD();
        Assertions.assertNotNull(coupons, "Coupon list must not be null");

        for (PopularCoupon coupon : coupons) {
            if (coupon.getEvents() == null) continue;

            for (CouponEvent event : coupon.getEvents()) {
                double minOdd = event.getMinOdd();
                double maxOdd = event.getMaxOdd();

                Assertions.assertTrue(minOdd <= maxOdd,
                        "Event (Code: " + event.getCode() + ", Name: " + event.getName() +
                                ") in coupon " + coupon.getCouponHash() +
                                " has minOdd (" + minOdd + ") greater than maxOdd (" + maxOdd + ")");
            }
        }
    }

    @Then("each event type must be consistent with its assigned sportId")
    public void eachEventTypeMustBeConsistentWithItsAssignedSportId() {
        PopularCouponsResponse responsePojo = getOrDeserializeResponse();
        List<PopularCoupon> coupons = responsePojo.getD();
        Assertions.assertNotNull(coupons, "Coupon list must not be null");

        for (PopularCoupon coupon : coupons) {
            if (coupon.getEvents() == null) continue;

            for (CouponEvent event : coupon.getEvents()) {
                int sportId = event.getSportId();
                String type = event.getType();

                boolean isConsistent = SportTypeMapper.isConsistent(sportId, type);
                Assertions.assertTrue(isConsistent,
                        "Event (Code: " + event.getCode() + ", Name: " + event.getName() +
                                ") has inconsistent sportId (" + sportId + ") and type ('" + type + "')");
            }
        }
    }

    @Then("coupons should be ordered by orderIndex in ascending order")
    public void couponsShouldBeOrderedByOrderIndexInAscendingOrder() {
        PopularCouponsResponse responsePojo = getOrDeserializeResponse();
        List<PopularCoupon> coupons = responsePojo.getD();
        Assertions.assertNotNull(coupons, "Coupon list must not be null");
        Assertions.assertTrue(coupons.size() > 1, "There should be more than 1 coupon to verify order");

        int previousOrderIndex = -1;
        for (int i = 0; i < coupons.size(); i++) {
            PopularCoupon coupon = coupons.get(i);
            int currentOrderIndex = coupon.getOrderIndex();

            if (i > 0) {
                Assertions.assertTrue(currentOrderIndex > previousOrderIndex,
                        "Coupons are not in strictly ascending order at index " + i +
                                "! Previous orderIndex: " + previousOrderIndex + ", Current: " + currentOrderIndex);
            }
            previousOrderIndex = currentOrderIndex;
        }
    }

    @Then("all coupons in the response should contain exactly {int} events")
    public void allCouponsInTheResponseShouldContainExactlyEvents(int expectedEventCount) {
        PopularCouponsResponse responsePojo = getOrDeserializeResponse();
        List<PopularCoupon> coupons = responsePojo.getD();
        Assertions.assertNotNull(coupons, "Coupon list must not be null");
        Assertions.assertFalse(coupons.isEmpty(), "Filtered coupon list should not be empty");

        for (PopularCoupon coupon : coupons) {
            Assertions.assertEquals(expectedEventCount, coupon.getEventCount(),
                    "Coupon " + coupon.getCouponHash() + " declared eventCount mismatch!");
            Assertions.assertEquals(expectedEventCount, coupon.getEvents().size(),
                    "Coupon " + coupon.getCouponHash() + " events list size mismatch!");
        }
    }

    @Then("the response error list {string} should contain error code {int} with an invalid parameter message")
    public void theResponseErrorListShouldContainErrorCodeWithAnInvalidParameterMessage(String errorListKey, int expectedErrorCode) {
        PopularCouponsResponse responsePojo = getOrDeserializeResponse();
        List<ErrorItem> errorList = responsePojo.getEl();

        Assertions.assertNotNull(errorList, "Error list '" + errorListKey + "' should not be null");
        Assertions.assertFalse(errorList.isEmpty(), "Error list '" + errorListKey + "' should not be empty");

        boolean errorFound = errorList.stream()
                .anyMatch(err -> err.getC() == expectedErrorCode && err.getM() != null && !err.getM().isEmpty());

        Assertions.assertTrue(errorFound,
                "Expected error code " + expectedErrorCode + " was not found in error list: " + errorList);
    }

    @Then("the response body must conform to JSON schema {string}")
    public void theResponseBodyMustConformToJsonSchema(String schemaPath) {
        Response response = getResponse();
        response.then().body(matchesJsonSchemaInClasspath(schemaPath));
    }

    @Then("the response can be mapped to the {string} POJO class")
    public void theResponseCanBeMappedToThePojoClass(String pojoClassName) {
        Response response = getResponse();
        PopularCouponsResponse pojo = response.as(PopularCouponsResponse.class);
        Assertions.assertNotNull(pojo, "Deserialized POJO should not be null");
        ScenarioContext.set(ContextKey.POPULAR_COUPONS_RESPONSE, pojo);
    }

    @Then("the deserialized POJO attributes should contain valid non-null coupon data")
    public void theDeserializedPojoAttributesShouldContainValidNonNullCouponData() {
        PopularCouponsResponse pojo = getOrDeserializeResponse();
        Assertions.assertEquals(200, pojo.getSc(), "POJO sc field should be 200");
        Assertions.assertNotNull(pojo.getD(), "POJO d (coupons) list should not be null");
        Assertions.assertFalse(pojo.getD().isEmpty(), "POJO d (coupons) list should contain coupons");

        PopularCoupon firstCoupon = pojo.getD().get(0);
        Assertions.assertNotNull(firstCoupon.getCouponHash(), "First coupon hash must not be null");
        Assertions.assertNotNull(firstCoupon.getCouponId(), "First coupon id must not be null");
        Assertions.assertTrue(firstCoupon.getOrderIndex() >= 1, "First coupon order index must be >= 1");
        Assertions.assertNotNull(firstCoupon.getEvents(), "First coupon events list must not be null");
        Assertions.assertFalse(firstCoupon.getEvents().isEmpty(), "First coupon events list must not be empty");

        CouponEvent firstEvent = firstCoupon.getEvents().get(0);
        Assertions.assertTrue(firstEvent.getBetradarId() > 0, "BetradarId should be positive");
        Assertions.assertNotNull(firstEvent.getType(), "Event type must not be null");
        Assertions.assertTrue(firstEvent.getSportId() > 0, "SportId must be positive");
        Assertions.assertNotNull(firstEvent.getOutcomes(), "Event outcomes must not be null");
    }

    @Then("the response time should be within the SLA threshold of {long} milliseconds")
    public void theResponseTimeShouldBeWithinTheSlaThresholdOfMilliseconds(long maxAllowedMs) {
        Response response = getResponse();
        long responseTime = response.getTime();
        Assertions.assertTrue(responseTime < maxAllowedMs,
                "Response time exceeded SLA! Max allowed: " + maxAllowedMs + " ms, Actual: " + responseTime + " ms");
    }

    @Then("the response header {string} should contain {string}")
    public void theResponseHeaderShouldContain(String headerName, String expectedHeaderValue) {
        Response response = getResponse();
        String actualHeaderValue = response.getHeader(headerName);
        Assertions.assertNotNull(actualHeaderValue, "Header '" + headerName + "' is missing in response");
        Assertions.assertTrue(actualHeaderValue.toLowerCase().contains(expectedHeaderValue.toLowerCase()),
                "Header '" + headerName + "' value ('" + actualHeaderValue + "') does not contain '" + expectedHeaderValue + "'");
    }

    @Then("the response header {string} should be present")
    public void theResponseHeaderShouldBePresent(String headerName) {
        Response response = getResponse();
        String actualHeaderValue = response.getHeader(headerName);
        Assertions.assertNotNull(actualHeaderValue, "Expected header '" + headerName + "' was not found in response");
        Assertions.assertFalse(actualHeaderValue.trim().isEmpty(), "Header '" + headerName + "' should not be empty");
    }

    @Then("all coupons must have a valid 32 character hex couponHash")
    public void allCouponsMustHaveAValid32CharacterHexCouponHash() {
        PopularCouponsResponse pojo = getOrDeserializeResponse();
        List<PopularCoupon> coupons = pojo.getD();
        Assertions.assertNotNull(coupons, "Coupons list must not be null");

        for (PopularCoupon coupon : coupons) {
            String hash = coupon.getCouponHash();
            Assertions.assertNotNull(hash, "couponHash should not be null");
            Assertions.assertTrue(MD5_HEX_PATTERN.matcher(hash).matches(),
                    "couponHash '" + hash + "' is not a valid 32-character hexadecimal MD5 hash");
        }
    }

    @Then("every event in each coupon must have at least one outcome with a positive odd value")
    public void everyEventInEachCouponMustHaveAtLeastOneOutcomeWithAPositiveOddValue() {
        PopularCouponsResponse pojo = getOrDeserializeResponse();
        List<PopularCoupon> coupons = pojo.getD();
        Assertions.assertNotNull(coupons, "Coupons list must not be null");

        for (PopularCoupon coupon : coupons) {
            if (coupon.getEvents() == null) continue;

            for (CouponEvent event : coupon.getEvents()) {
                List<EventOutcome> outcomes = event.getOutcomes();
                Assertions.assertNotNull(outcomes, "Outcomes list for event " + event.getCode() + " should not be null");
                Assertions.assertFalse(outcomes.isEmpty(), "Outcomes list for event " + event.getCode() + " should not be empty");

                for (EventOutcome outcome : outcomes) {
                    Assertions.assertNotNull(outcome.getOdd(), "Outcome odd must not be null");
                    // Nesine returns odds formatted e.g. "1,72" or "1.72"
                    String normalizedOdd = outcome.getOdd().replace(",", ".");
                    double parsedOdd = Double.parseDouble(normalizedOdd);
                    Assertions.assertTrue(parsedOdd > 1.0,
                            "Outcome odd should be greater than 1.0, but was: " + parsedOdd + " for event " + event.getCode());
                }
            }
        }
    }

    // --- Helper methods ---

    private Response getResponse() {
        Response response = ScenarioContext.get(ContextKey.RESPONSE, Response.class);
        Assertions.assertNotNull(response, "Response is not available in ScenarioContext! Check if GET request step ran.");
        return response;
    }

    private PopularCouponsResponse getOrDeserializeResponse() {
        PopularCouponsResponse pojo = ScenarioContext.get(ContextKey.POPULAR_COUPONS_RESPONSE, PopularCouponsResponse.class);
        if (pojo == null) {
            Response response = getResponse();
            pojo = response.as(PopularCouponsResponse.class);
            ScenarioContext.set(ContextKey.POPULAR_COUPONS_RESPONSE, pojo);
        }
        return pojo;
    }
}
