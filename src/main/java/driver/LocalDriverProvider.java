package driver;

import config.ConfigReader;
import exceptions.FrameworkException;
import io.appium.java_client.android.AndroidDriver;
import org.slf4j.Logger;
import utils.LogUtils;

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

            String appPackage =
                    ConfigReader.get("appPackage");

            LOGGER.info(
                    "Activating Android application: {}",
                    appPackage
            );

            driver.activateApp(appPackage);

            LOGGER.info(
                    "Android application activated successfully: {}",
                    appPackage
            );

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
}