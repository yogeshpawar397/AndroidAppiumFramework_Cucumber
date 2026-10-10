
package driver;

import config.ConfigReader;
import constants.ExecutionMode;
import exceptions.FrameworkException;
import io.appium.java_client.android.AndroidDriver;
import org.slf4j.Logger;
import utils.LogUtils;

public final class DriverManager {

    private static final Logger LOGGER =
            LogUtils.getLogger(DriverManager.class);

    private static final ThreadLocal<AndroidDriver> DRIVER =
            new ThreadLocal<>();

    private DriverManager() {
        // Utility class
    }

    public static void initializeDriver() {

        if (DRIVER.get() != null) {
            throw new IllegalStateException(
                    "AndroidDriver is already initialized for the current thread.");
        }

        ConfigReader.validateRequiredProperties(
                "platformName",
                "automationName",
                "deviceName",
                "explicitWait",
                "driverInitRetries",
                "executionMode"
        );

        int maxAttempts = ConfigReader.getInt("driverInitRetries");

        if (maxAttempts < 1) {
            throw new FrameworkException(
                    "driverInitRetries must be at least 1.");
        }

        if (ConfigReader.getExecutionMode() == ExecutionMode.LOCAL) {
            ConfigReader.validateRequiredProperties("appiumServerUrl");
        }

        Exception lastException = null;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                LOGGER.info(
                        "Initializing AndroidDriver. Attempt {}/{}",
                        attempt, maxAttempts);

                AndroidDriver driver = DriverFactory.createAndroidDriver();

                if (driver == null) {
                    throw new FrameworkException(
                            "DriverFactory returned a null AndroidDriver.");
                }

                DRIVER.set(driver);

                LOGGER.info("AndroidDriver initialized successfully.");
                return;

            } catch (Exception e) {
                lastException = e;

                LOGGER.error(
                        "Driver initialization attempt {}/{} failed.",
                        attempt, maxAttempts, e);

                if (attempt < maxAttempts) {
                    LOGGER.info("Retrying AndroidDriver initialization...");
                }
            }
        }

        throw new FrameworkException(
                "Failed to initialize AndroidDriver after "
                        + maxAttempts + " attempts.",
                lastException);
    }

    public static AndroidDriver getDriver() {
        AndroidDriver driver = DRIVER.get();

        if (driver == null) {
            throw new IllegalStateException(
                    "AndroidDriver is not initialized for the current thread.");
        }

        return driver;
    }

    public static boolean isDriverInitialized() {
        return DRIVER.get() != null;
    }

    public static void quitDriver() {
        AndroidDriver driver = DRIVER.get();

        try {
            if (driver != null) {
                LOGGER.info("Quitting AndroidDriver...");
                driver.quit();
                LOGGER.info("AndroidDriver quit successfully.");
            }
        } catch (Exception e) {
            LOGGER.error("Failed to quit AndroidDriver cleanly.", e);
        } finally {
            DRIVER.remove();
            LOGGER.debug("AndroidDriver reference removed from current thread.");
        }
    }
}
