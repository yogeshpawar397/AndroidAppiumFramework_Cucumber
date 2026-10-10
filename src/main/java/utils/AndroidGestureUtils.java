
package utils;

import driver.DriverManager;
import exceptions.FrameworkException;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class AndroidGestureUtils {

    private static final Logger LOGGER =
            LogUtils.getLogger(AndroidGestureUtils.class);

    private static final double DEFAULT_PERCENT = 0.75;
    private static final int DEFAULT_MAX_SCROLLS = 5;
    private static final int SCREEN_MARGIN = 20;

    private AndroidGestureUtils() {
        // Utility class
    }

    private static AndroidDriver getDriver() {
        return DriverManager.getDriver();
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    /**
     * Swipe across the screen in the requested direction.
     *
     * @param direction swipe direction
     * @param percent proportion of the screen to swipe (0.1 to 1.0)
     */
    public static void swipe(Direction direction, double percent) {
        validateDirection(direction);
        validatePercent(percent);

        try {
            AndroidDriver driver = getDriver();
            Dimension size = driver.manage().window().getSize();

            Map<String, Object> args = new HashMap<>();
            args.put("left", SCREEN_MARGIN);
            args.put("top", SCREEN_MARGIN);
            args.put("width", size.getWidth() - 2 * SCREEN_MARGIN);
            args.put("height", size.getHeight() - 2 * SCREEN_MARGIN);
            args.put("direction", direction.name().toLowerCase());
            args.put("percent", percent);

            LOGGER.info("Swiping screen in direction: {}", direction);

            driver.executeScript("mobile: swipeGesture", args);

        } catch (Exception e) {
            LOGGER.error("Failed to swipe in direction: {}", direction, e);
            throw new FrameworkException(
                    "Failed to swipe in direction: " + direction, e);
        }
    }

    /**
     * Scroll until an element becomes visible or the scroll limit is reached.
     *
     * @return true if the element becomes visible; otherwise false
     */
    public static boolean scrollUntilVisible(
            By locator,
            Direction direction,
            double percent,
            int maxScrolls) {

        validateDirection(direction);
        validatePercent(percent);

        if (locator == null) {
            throw new IllegalArgumentException(
                    "Element locator must not be null.");
        }

        if (maxScrolls < 1) {
            throw new IllegalArgumentException(
                    "Maximum scroll count must be at least 1.");
        }

        try {
            AndroidDriver driver = getDriver();

            for (int attempt = 0; attempt <= maxScrolls; attempt++) {

                if (isElementDisplayed(driver, locator)) {
                    LOGGER.info("Element is visible: {}", locator);
                    return true;
                }

                if (attempt == maxScrolls) {
                    break;
                }

                Dimension size = driver.manage().window().getSize();

                Map<String, Object> args = new HashMap<>();
                args.put("left", SCREEN_MARGIN);
                args.put("top", SCREEN_MARGIN);
                args.put("width", size.getWidth() - 2 * SCREEN_MARGIN);
                args.put("height", size.getHeight() - 2 * SCREEN_MARGIN);
                args.put("direction", direction.name().toLowerCase());
                args.put("percent", percent);

                LOGGER.debug(
                        "Scroll attempt {} of {} for element: {}",
                        attempt + 1, maxScrolls, locator);

                Boolean canScrollMore = (Boolean) driver.executeScript(
                        "mobile: scrollGesture", args);

                if (Boolean.FALSE.equals(canScrollMore)) {
                    break;
                }
            }

            LOGGER.warn(
                    "Element not visible after scrolling: {}", locator);
            return false;

        } catch (Exception e) {
            LOGGER.error(
                    "Failed while scrolling for element: {}", locator, e);
            throw new FrameworkException(
                    "Failed while scrolling for element: " + locator, e);
        }
    }

    /**
     * Scroll using the default percentage and maximum attempts.
     */
    public static boolean scrollUntilVisible(
            By locator, Direction direction) {

        return scrollUntilVisible(
                locator,
                direction,
                DEFAULT_PERCENT,
                DEFAULT_MAX_SCROLLS);
    }

    /**
     * Hide the Android keyboard if it is open.
     */
    public static void hideKeyboard() {
        try {
            LOGGER.debug("Attempting to hide Android keyboard");
            getDriver().hideKeyboard();

        } catch (WebDriverException e) {
            // Hiding may fail when the keyboard is already closed.
            LOGGER.debug(
                    "Keyboard is already hidden or cannot be dismissed.");
        } catch (Exception e) {
            throw new FrameworkException(
                    "Failed to hide Android keyboard.", e);
        }
    }

    /**
     * Press the Android Back key.
     */
    public static void pressBack() {
        try {
            LOGGER.info("Pressing Android Back key");
            getDriver().pressKey(new KeyEvent(AndroidKey.BACK));

        } catch (Exception e) {
            LOGGER.error("Failed to press Android Back key.", e);
            throw new FrameworkException(
                    "Failed to press Android Back key.", e);
        }
    }

    private static boolean isElementDisplayed(
            AndroidDriver driver, By locator) {

        List<WebElement> elements = driver.findElements(locator);

        for (WebElement element : elements) {
            try {
                if (element.isDisplayed()) {
                    return true;
                }
            } catch (StaleElementReferenceException e) {
                LOGGER.debug("Element became stale during visibility check");
            }
        }

        return false;
    }

    private static void validateDirection(Direction direction) {
        if (direction == null) {
            throw new IllegalArgumentException(
                    "Gesture direction must not be null.");
        }
    }

    private static void validatePercent(double percent) {
        if (percent < 0.1 || percent > 1.0
                || Double.isNaN(percent)
                || Double.isInfinite(percent)) {
            throw new IllegalArgumentException(
                    "Gesture percentage must be between 0.1 and 1.0.");
        }
    }
}


// Refer below code to use the AndroidGestureUtils class in your test steps or page objects.
//import org.openqa.selenium.By;
//import utils.AndroidGestureUtils;
//import utils.AndroidGestureUtils.Direction;
//
//public void scrollToAccessibilityOption() {
//    By accessibilityOption =
//            By.xpath("//*[@text='Accessibility Node Provider']");
//
//    boolean visible =
//            AndroidGestureUtils.scrollUntilVisible(
//                    accessibilityOption, Direction.DOWN);
//
//    if (!visible) {
//        throw new AssertionError(
//                "Accessibility Node Provider was not found after scrolling.");
//    }
//}
//
//
//// Swipe up
//AndroidGestureUtils.swipe(Direction.UP, 0.75);
//
//// Swipe left
//AndroidGestureUtils.swipe(Direction.LEFT, 0.60);
//
//// Scroll down until an element appears
//boolean visible = AndroidGestureUtils.scrollUntilVisible(
//        By.id("your.resource.id"),
//        Direction.DOWN,
//        0.70,
//        4);
//
//// Hide the keyboard
//AndroidGestureUtils.hideKeyboard();
//
//// Press Android Back
//AndroidGestureUtils.pressBack();