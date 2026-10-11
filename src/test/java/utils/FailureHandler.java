package utils;

import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;

public final class FailureHandler {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(FailureHandler.class);

    private FailureHandler() {
        // Utility class
    }

    public static void handleFailure(
            Scenario scenario) {

        if (scenario == null) {
            LOGGER.warn(
                    "Cannot handle failure because Scenario is null."
            );
            return;
        }

        LOGGER.error(
                "Scenario failed: {}",
                scenario.getName()
        );

        byte[] screenshot =
                ScreenshotUtils.takeScreenshot();

        if (screenshot != null) {

            scenario.attach(
                    screenshot,
                    "image/png",
                    "Failure Screenshot"
            );
            Allure.addAttachment(
                    "Failure Screenshot",
                    "image/png",
                    new ByteArrayInputStream(screenshot),
                    ".png"
            );

            LOGGER.info(
                    "Failure screenshot attached for scenario: {}",
                    scenario.getName()
            );

        } else {

            LOGGER.warn(
                    "Failure screenshot could not be captured for scenario: {}",
                    scenario.getName()
            );
        }
    }
}