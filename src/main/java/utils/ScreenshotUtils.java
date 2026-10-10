package utils;

import driver.DriverManager;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.OutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ScreenshotUtils {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ScreenshotUtils.class);

    private ScreenshotUtils() {
        // Utility class
    }

    public static byte[] takeScreenshot() {

        if (!DriverManager.isDriverInitialized()) {

            LOGGER.warn(
                    "Screenshot skipped because AndroidDriver "
                            + "is not initialized for the current thread."
            );

            return null;
        }

        try {

            AndroidDriver driver =
                    DriverManager.getDriver();

            return driver.getScreenshotAs(
                    OutputType.BYTES
            );

        } catch (Exception e) {

            LOGGER.error(
                    "Failed to capture screenshot.",
                    e
            );

            return null;
        }
    }
}