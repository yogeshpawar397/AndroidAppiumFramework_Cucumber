
package utils;

import config.ConfigReader;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class WaitUtils {

    private WaitUtils() {
        // Utility class
    }

    private static long getTimeout() {
        long timeout = ConfigReader.getInt("explicitWait");

        if (timeout <= 0) {
            throw new IllegalArgumentException(
                    "explicitWait must be greater than zero."
            );
        }

        return timeout;
    }

    private static void validateArguments(
            AndroidDriver driver,
            By locator) {

        if (driver == null) {
            throw new IllegalArgumentException(
                    "AndroidDriver must not be null."
            );
        }

        if (locator == null) {
            throw new IllegalArgumentException(
                    "Element locator must not be null."
            );
        }
    }

    private static WebDriverWait createWait(AndroidDriver driver) {

        WebDriverWait wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(getTimeout())
        );

        wait.ignoring(StaleElementReferenceException.class);

        return wait;
    }

    public static WebElement waitForPresence(
            AndroidDriver driver,
            By locator) {

        validateArguments(driver, locator);

        return createWait(driver).until(
                ExpectedConditions.presenceOfElementLocated(locator)
        );
    }

    public static WebElement waitForVisibility(
            AndroidDriver driver,
            By locator) {

        validateArguments(driver, locator);

        return createWait(driver).until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
    }

    public static WebElement waitForClickable(
            AndroidDriver driver,
            By locator) {

        validateArguments(driver, locator);

        return createWait(driver).until(
                ExpectedConditions.elementToBeClickable(locator)
        );
    }

    public static boolean waitForElementToDisappear(
            AndroidDriver driver,
            By locator) {

        validateArguments(driver, locator);

        return createWait(driver).until(
                ExpectedConditions.invisibilityOfElementLocated(locator)
        );
    }
}
