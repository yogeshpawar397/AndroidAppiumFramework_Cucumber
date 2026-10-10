
package hooks;

import com.fasterxml.jackson.databind.JsonNode;
import constants.ScenarioContextKeys;
import context.ScenarioContextFactory;
import driver.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.PageObjectManagerFactory;
import utils.FailureHandler;
import utils.TestDataReader;

import java.util.Collection;

public class CucumberHooks {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(CucumberHooks.class);

    private static final String API_ONLY_TAG = "@api-only";

    @Before
    public void setUp(Scenario scenario) {

        Collection<String> tags = scenario.getSourceTagNames();

        LOGGER.info(
                "========== Scenario Started: {} ==========",
                scenario.getName());

        ScenarioContextFactory.initialize();

        if (tags.contains(API_ONLY_TAG) && tags.contains("@ui")) {
            throw new IllegalStateException(
                    "A scenario cannot be tagged both @api-only and @ui.");
        }

        if (tags.contains(API_ONLY_TAG) && !tags.contains("@api")) {
            throw new IllegalStateException(
                    "A scenario tagged @api-only must also have the @api tag.");
        }

        if (tags.contains(API_ONLY_TAG)) {
            LOGGER.info(
                    "API-only scenario detected. Mobile initialization skipped.");
            return;
        }

        // Load ApiDemos test data only when needed.
        if (tags.contains("@apidemos")) {
            JsonNode testData =
                    TestDataReader.read("api-demos/test-data.json");

            JsonNode menus = testData.path("menus");

            String accessibility =
                    menus.path("accessibility").asText();

            String accessibilityNodeProvider =
                    menus.path("accessibilityNodeProvider").asText();

            if (accessibility.isBlank()
                    || accessibilityNodeProvider.isBlank()) {
                throw new IllegalStateException(
                        "Required accessibility test data is missing.");
            }

            ScenarioContextFactory.get().set(
                    ScenarioContextKeys.ACCESSIBILITY,
                    accessibility);

            ScenarioContextFactory.get().set(
                    ScenarioContextKeys.ACCESSIBILITY_NODE_PROVIDER,
                    accessibilityNodeProvider);
        }

        DriverManager.initializeDriver();
        PageObjectManagerFactory.initialize();

        LOGGER.info("Mobile test context initialized successfully.");
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                try {
                    FailureHandler.handleFailure(scenario);
                } catch (Exception e) {
                    LOGGER.error(
                            "Failure handling failed for scenario: {}",
                            scenario.getName(), e);
                }
            } else {
                LOGGER.info("Scenario passed: {}", scenario.getName());
            }
        } finally {
            try {
                PageObjectManagerFactory.remove();
            } finally {
                try {
                    DriverManager.quitDriver();
                } finally {
                    try {
                        ScenarioContextFactory.remove();
                    } finally {
                        LOGGER.info(
                                "========== Scenario Finished: {} ==========",
                                scenario.getName());
                    }
                }
            }
        }
    }
}
