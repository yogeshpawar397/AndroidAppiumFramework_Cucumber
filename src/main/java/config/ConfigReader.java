package config;

import exceptions.FrameworkException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import constants.FrameworkConstants;
import constants.ExecutionMode;

import exceptions.FrameworkException;

public final class ConfigReader {

    private static final Properties PROPERTIES =
            new Properties();

    static {

        String environment =
                System.getProperty(
                        "environment",
                        "local"
                );

        loadProperties(
                FrameworkConstants.COMMON_CONFIG_FILE
        );

        String environmentFile =
                FrameworkConstants.ENVIRONMENT_CONFIG_PREFIX
                        + environment
                        + FrameworkConstants.ENVIRONMENT_CONFIG_SUFFIX;

        loadProperties(environmentFile);
    }

    private ConfigReader() {
    }

    private static void loadProperties(String fileName) {

        try (InputStream input =
                     ConfigReader.class
                             .getClassLoader()
                             .getResourceAsStream(fileName)) {

            if (input == null) {
                throw new FrameworkException(
                        "Configuration file not found: "
                                + fileName
                );
            }

            PROPERTIES.load(input);

        } catch (IOException e) {

            throw new FrameworkException(
                    "Unable to load configuration file: "
                            + fileName,
                    e
            );
        }
    }

    public static String get(String key) {

        String value =
                PROPERTIES.getProperty(key);

        if (value == null) {
            throw new FrameworkException(
                    "Missing configuration key: " + key
            );
        }

        return value.trim();
    }

    public static String getOrDefault(
            String key,
            String defaultValue) {

        return PROPERTIES
                .getProperty(key, defaultValue)
                .trim();
    }

    public static int getInt(String key) {

        return Integer.parseInt(get(key));
    }

    public static boolean getBoolean(String key) {

        return Boolean.parseBoolean(get(key));
    }

    public static void validateRequiredProperties(
            String... keys) {

        for (String key : keys) {

            String value =
                    PROPERTIES.getProperty(key);

            if (value == null ||
                    value.trim().isEmpty()) {

                throw new FrameworkException(
                        "Required configuration property "
                                + "is missing or empty: "
                                + key
                );
            }
        }
    }

    public static ExecutionMode getExecutionMode() {

        String executionMode =
                System.getProperty(
                        "executionMode",
                        get("executionMode")
                );

        try {

            return ExecutionMode.valueOf(
                    executionMode.trim().toUpperCase()
            );

        } catch (IllegalArgumentException e) {

            throw new FrameworkException(
                    "Invalid executionMode: "
                            + executionMode
                            + ". Supported values are LOCAL and BROWSERSTACK.",
                    e
            );
        }
    }

    public static String getBrowserStackUsername() {

        String username =
                System.getenv("BROWSERSTACK_USERNAME");

        if (username == null || username.isBlank()) {
            throw new FrameworkException(
                    "BROWSERSTACK_USERNAME environment variable is not set."
            );
        }

        return username.trim();
    }

    public static String getBrowserStackAccessKey() {

        String accessKey =
                System.getenv("BROWSERSTACK_ACCESS_KEY");

        if (accessKey == null || accessKey.isBlank()) {
            throw new FrameworkException(
                    "BROWSERSTACK_ACCESS_KEY environment variable is not set."
            );
        }

        return accessKey.trim();
    }

    public static String getEnvironmentVariable(String key) {

        String value = System.getenv(key);

        if (value == null || value.isBlank()) {
            throw new FrameworkException(
                    "Required environment variable is missing: " + key
            );
        }

        return value;
    }
}