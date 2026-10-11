package driver;

import config.ConfigReader;
import exceptions.FrameworkException;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class CapabilityManager {

    private CapabilityManager() {
        // Utility class
    }

    public static UiAutomator2Options getAndroidOptions() {

        UiAutomator2Options options = new UiAutomator2Options();

        options.setPlatformName(
                ConfigReader.get("platformName")
        );

        options.setAutomationName(
                ConfigReader.get("automationName")
        );

        options.setDeviceName(
                ConfigReader.get("deviceName")
        );

        String udid =
                ConfigReader.getOrDefault("udid", "");

        if (!udid.isBlank()) {
            options.setUdid(udid);
        }

        options.setNoReset(
                ConfigReader.getBoolean("noReset")
        );
        options.setEnforceAppInstall(false);

        String app =
                ConfigReader.getOrDefault("app", "");

        if (!app.isBlank()) {

            Path appPath =
                    Paths.get(app);

            if (!appPath.isAbsolute()) {
                appPath =
                        Paths.get(
                                System.getProperty("user.dir"),
                                app
                        );
            }

            appPath =
                    appPath.toAbsolutePath().normalize();

            if (!Files.exists(appPath)) {
                throw new FrameworkException(
                        "Application file not found: "
                                + appPath
                );
            }

            options.setApp(appPath.toString());
        }

        String appPackage =
                ConfigReader.getOrDefault("appPackage", "");

        if (!appPackage.isBlank()) {
            options.setAppPackage(appPackage);
        }

        String appActivity =
                ConfigReader.getOrDefault("appActivity", "");

        if (!appActivity.isBlank()) {
            options.setAppActivity(appActivity);
        }

        return options;
    }
}