package pages;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import utils.LogUtils;
import exceptions.FrameworkException;

public class SettingsPage extends BasePage {

    private static final Logger LOGGER =
            LogUtils.getLogger(SettingsPage.class);

    private static final String SETTINGS_PACKAGE =
            "com.android.settings";

    private final By settingsTitle =
            AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"Settings\")"
            );

    public void open() {

        try {
            LOGGER.info("Opening Android Settings application");

            launchApp(SETTINGS_PACKAGE);

            LOGGER.info("Android Settings application opened successfully");

        } catch (Exception e) {

            LOGGER.error(
                    "Failed to open Android Settings application",
                    e
            );

            throw new FrameworkException(
                    "Failed to open Android Settings application",
                    e
            );
        }
    }

    public boolean isSettingsPageDisplayed() {

        try {
            LOGGER.info("Verifying Settings page is displayed");

            boolean displayed = isDisplayed(settingsTitle);

            LOGGER.info(
                    "Settings page displayed: {}",
                    displayed
            );

            return displayed;

        } catch (Exception e) {

            LOGGER.error(
                    "Failed to verify Settings page",
                    e
            );

            throw new FrameworkException(
                    "Failed to verify Settings page",
                    e
            );
        }
    }
}