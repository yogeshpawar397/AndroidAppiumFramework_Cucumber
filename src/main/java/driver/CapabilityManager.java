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

        configureAutomaticChromeDriverDownload(options);

        String chromeDriverExecutable =
                ConfigReader.getOrDefault("chromeDriverExecutable", "");

        if (!chromeDriverExecutable.isBlank()) {
            Path chromeDriverPath = Paths.get(chromeDriverExecutable);

            if (!chromeDriverPath.isAbsolute()) {
                chromeDriverPath = Paths.get(
                        System.getProperty("user.dir"),
                        chromeDriverExecutable
                );
            }

            chromeDriverPath =
                    chromeDriverPath.toAbsolutePath().normalize();

            if (!Files.isRegularFile(chromeDriverPath)) {
                throw new FrameworkException(
                        "ChromeDriver executable not found: "
                                + chromeDriverPath
                );
            }

            options.setChromedriverExecutable(chromeDriverPath.toString());
        }

        return options;
    }

    private static void configureAutomaticChromeDriverDownload(
            UiAutomator2Options options) {

        String executableDirectory = ConfigReader.getOrDefault(
                "chromeDriverExecutableDir",
                "target/chromedrivers"
        );

        Path executableDirectoryPath = Paths.get(executableDirectory);

        if (!executableDirectoryPath.isAbsolute()) {
            executableDirectoryPath = Paths.get(
                    System.getProperty("user.dir"),
                    executableDirectory
            );
        }

        options.setCapability(
                "appium:chromedriverExecutableDir",
                executableDirectoryPath.toAbsolutePath().normalize().toString()
        );
    }
}