package pages;

import config.ConfigReader;
import driver.DriverManager;
import exceptions.FrameworkException;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ElementUtils;

public class BasePage {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(BasePage.class);

    protected AndroidDriver getDriver() {
        return DriverManager.getDriver();
    }

    protected void click(By locator) {
        ElementUtils.click(locator);
    }

    protected void type(By locator, String text) {
        ElementUtils.type(locator, text);
    }

    protected String getText(By locator) {
        return ElementUtils.getText(locator);
    }

    protected boolean isDisplayed(By locator) {
        return ElementUtils.isDisplayed(locator);
    }

    protected String getAttribute(
            By locator,
            String attribute) {

        return ElementUtils.getAttribute(locator, attribute);
    }

    protected void clear(By locator) {

        ElementUtils.clear(locator);
    }

    protected void hideKeyboard() {

        LOGGER.info("Hiding keyboard");

        try {
            getDriver().hideKeyboard();
        } catch (Exception e) {
            LOGGER.debug(
                    "Keyboard was already hidden or could not be hidden"
            );
        }
    }

    protected void pressBack() {

        LOGGER.info("Pressing Android Back button");

        getDriver().navigate().back();
    }

    protected void pressHome() {

        LOGGER.info("Pressing Android Home button");

        getDriver().pressKey(
                new KeyEvent(AndroidKey.HOME)
        );
    }

    protected void launchApp(String packageName) {

        if (packageName == null || packageName.isBlank()) {
            throw new FrameworkException(
                    "Application package name must not be null or blank."
            );
        }

        getDriver().activateApp(packageName);
    }

    protected void closeApp(String packageName) {

        LOGGER.info(
                "Closing application: {}",
                packageName
        );

        getDriver().terminateApp(packageName);
    }

    protected boolean isAppInstalled(String packageName) {

        LOGGER.info(
                "Checking whether application is installed: {}",
                packageName
        );

        return getDriver().isAppInstalled(packageName);
    }
}