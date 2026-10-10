
package utils;

import driver.DriverManager;
import exceptions.FrameworkException;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;

public final class ElementUtils {

    private static final Logger LOGGER =
            LogUtils.getLogger(ElementUtils.class);

    private ElementUtils() {
        // Utility class
    }

    private static AndroidDriver getDriver() {
        return DriverManager.getDriver();
    }

    public static void click(By locator) {
        try {
            LOGGER.info("Clicking element: {}", locator);

            WaitUtils.waitForClickable(
                    getDriver(), locator
            ).click();

        } catch (Exception e) {
            LOGGER.error("Failed to click element: {}", locator, e);

            throw new FrameworkException(
                    "Failed to click element: " + locator, e
            );
        }
    }

    public static void type(By locator, String text) {
        if (text == null) {
            throw new IllegalArgumentException(
                    "Text to type must not be null."
            );
        }

        try {
            LOGGER.info("Typing into element: {}", locator);

            WebElement element =
                    WaitUtils.waitForVisibility(getDriver(), locator);

            element.clear();
            element.sendKeys(text);

        } catch (Exception e) {
            LOGGER.error("Failed to type into element: {}", locator, e);

            throw new FrameworkException(
                    "Failed to type into element: " + locator, e
            );
        }
    }

    public static String getText(By locator) {
        try {
            LOGGER.info("Getting text from element: {}", locator);

            return WaitUtils.waitForVisibility(
                    getDriver(), locator
            ).getText();

        } catch (Exception e) {
            LOGGER.error("Failed to get text from element: {}", locator, e);

            throw new FrameworkException(
                    "Failed to get text from element: " + locator, e
            );
        }
    }

    public static boolean isDisplayed(By locator) {
        try {
            LOGGER.info("Checking element visibility: {}", locator);

            return WaitUtils.waitForVisibility(
                    getDriver(), locator
            ).isDisplayed();

        } catch (TimeoutException e) {
            LOGGER.debug("Element was not visible before timeout: {}", locator);
            return false;

        } catch (Exception e) {
            LOGGER.error("Failed to check element visibility: {}", locator, e);

            throw new FrameworkException(
                    "Failed to check element visibility: " + locator, e
            );
        }
    }

    public static String getAttribute(
            By locator,
            String attribute) {

        try {
            LOGGER.info(
                    "Getting attribute '{}' from element: {}",
                    attribute, locator
            );

            return WaitUtils.waitForVisibility(
                    getDriver(), locator
            ).getAttribute(attribute);

        } catch (Exception e) {
            LOGGER.error(
                    "Failed to get attribute '{}' from element: {}",
                    attribute, locator, e
            );

            throw new FrameworkException(
                    "Failed to get attribute '" + attribute
                            + "' from element: " + locator, e
            );
        }
    }

    public static void clear(By locator) {
        try {
            LOGGER.info("Clearing element: {}", locator);

            WaitUtils.waitForVisibility(
                    getDriver(), locator
            ).clear();

        } catch (Exception e) {
            LOGGER.error("Failed to clear element: {}", locator, e);

            throw new FrameworkException(
                    "Failed to clear element: " + locator, e
            );
        }
    }
}
