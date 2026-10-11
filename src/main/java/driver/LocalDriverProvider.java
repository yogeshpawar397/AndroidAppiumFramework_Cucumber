package driver;

import config.ConfigReader;
import exceptions.FrameworkException;
import io.appium.java_client.android.AndroidDriver;
import org.slf4j.Logger;
import utils.LogUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.MalformedURLException;
import java.net.URL;

public class LocalDriverProvider implements DriverProvider {

    private static final Logger LOGGER =
            LogUtils.getLogger(LocalDriverProvider.class);

    @Override
    public AndroidDriver createDriver() {

        String serverUrl =
                ConfigReader.get("appiumServerUrl");

        try {

            LOGGER.info(
                    "Creating local AndroidDriver using Appium server: {}",
                    serverUrl
            );

            AndroidDriver driver =
                    new AndroidDriver(
                            new URL(serverUrl),
                            CapabilityManager.getAndroidOptions()
                    );

            LOGGER.info(
                    "AndroidDriver created successfully."
            );

            installIfMissing(driver);

            return driver;

        } catch (MalformedURLException e) {

            LOGGER.error(
                    "Invalid Appium server URL: {}",
                    serverUrl,
                    e
            );

            throw new FrameworkException(
                    "Invalid Appium server URL: " + serverUrl,
                    e
            );

        } catch (Exception e) {

            LOGGER.error(
                    "Failed to create or activate Android application.",
                    e
            );

            throw new FrameworkException(
                    "Failed to create or activate Android application.",
                    e
            );
        }
    }

    private void installIfMissing(AndroidDriver driver) {

        String appPackage = ConfigReader.get("appPackage");

        LOGGER.info("Checking Android application installation: {}", appPackage);

        if (!driver.isAppInstalled(appPackage)) {
            Path appPath = resolveAppPath();

            LOGGER.info(
                    "Application '{}' is not installed. Installing from {}",
                    appPackage,
                    appPath
            );
            driver.installApp(appPath.toString());

            if (!driver.isAppInstalled(appPackage)) {
                throw new FrameworkException(
                        "Application installation completed, but package "
                                + appPackage + " is still not installed."
                );
            }
        } else {
            LOGGER.info("Application is already installed: {}", appPackage);
        }

        LOGGER.info("Activating Android application: {}", appPackage);
        driver.activateApp(appPackage);
        LOGGER.info(
                "Android application activated successfully: {}",
                appPackage
        );
    }

    private Path resolveAppPath() {

        String configuredApp = ConfigReader.getOrDefault("app", "");
        if (configuredApp.isBlank()) {
            throw new FrameworkException(
                    "Application package is not installed and no APK path "
                            + "is configured in the 'app' property."
            );
        }

        Path appPath = Paths.get(configuredApp);
        if (!appPath.isAbsolute()) {
            appPath = Paths.get(
                    System.getProperty("user.dir"),
                    configuredApp
            );
        }

        appPath = appPath.toAbsolutePath().normalize();
        if (!Files.isRegularFile(appPath)) {
            throw new FrameworkException(
                    "Application APK file not found: " + appPath
            );
        }

        return appPath;
    }
}