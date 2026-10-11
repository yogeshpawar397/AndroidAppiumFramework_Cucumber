package pages;

import config.ConfigReader;
import exceptions.FrameworkException;
import io.appium.java_client.NoSuchContextException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import utils.LogUtils;

import java.time.Duration;

public class WebBrowserPage extends BasePage {

    private static final Logger LOGGER =
            LogUtils.getLogger(WebBrowserPage.class);

    private static final By SEARCH_FIELD = By.name("q");

    public void switchToWebBrowser() {

        String webViewContext = "WEBVIEW_" + ConfigReader.get("appPackage");
        LOGGER.info("Waiting for web browser context: {}", webViewContext);

        String availableContext = new WebDriverWait(
                getDriver(),
                Duration.ofSeconds(ConfigReader.getInt("explicitWait"))
        ).until(driver -> getDriver()
                .getContextHandles()
                .stream()
                .filter(context -> context.equalsIgnoreCase(webViewContext))
                .findFirst()
                .orElse(null));

        try {
            getDriver().context(availableContext);
        } catch (NoSuchContextException e) {
            throw new FrameworkException(
                    "Could not switch to " + availableContext
                            + ". Ensure the Appium server handling this "
                            + "session was started with "
                            + "'appium server --allow-insecure "
                            + "uiautomator2:chromedriver_autodownload'. "
                            + "Stop any existing server on the configured "
                            + "port, then run tools/start-appium-webview.ps1.",
                    e
            );
        }

        new WebDriverWait(
                getDriver(),
                Duration.ofSeconds(ConfigReader.getInt("explicitWait"))
        ).until(ExpectedConditions.visibilityOfElementLocated(SEARCH_FIELD));

        LOGGER.info("Switched to web browser context");
    }

    public void search(String query) {

        if (query == null || query.isBlank()) {
            throw new FrameworkException(
                    "Web browser search query must not be blank."
            );
        }

        LOGGER.info("Searching the web browser for '{}'", query);

        WebElement searchField = new WebDriverWait(
                getDriver(),
                Duration.ofSeconds(ConfigReader.getInt("explicitWait"))
        ).until(ExpectedConditions.visibilityOfElementLocated(SEARCH_FIELD));

        searchField.clear();
        searchField.sendKeys(query, Keys.ENTER);
    }

    public void returnToNativeApp() {
        LOGGER.info("Returning to the native application context");
        getDriver().context("NATIVE_APP");
    }
}
