package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import utils.ContextKey;
import utils.ScenarioContext;

import java.nio.charset.StandardCharsets;

public class Hooks {

    @Before
    public void setUp(Scenario scenario) {
        // Clear thread-local scenario context to ensure clean state per scenario
        ScenarioContext.clear();
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            // If the scenario failed, attach response body to Allure for troubleshooting
            if (scenario.isFailed() && ScenarioContext.contains(ContextKey.RESPONSE)) {
                Response response = ScenarioContext.get(ContextKey.RESPONSE, Response.class);
                if (response != null && response.getBody() != null) {
                    Allure.addAttachment(
                            "Failed Scenario Response Body",
                            "application/json",
                            response.getBody().asPrettyString(),
                            ".json"
                    );
                }
            }
        } finally {
            // Ensure thread context is always cleared after scenario finishes
            ScenarioContext.clear();
        }
    }
}
