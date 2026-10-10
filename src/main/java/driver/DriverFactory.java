package driver;

import config.ConfigReader;
import constants.ExecutionMode;
import exceptions.FrameworkException;
import io.appium.java_client.android.AndroidDriver;
import org.slf4j.Logger;
import utils.LogUtils;

public final class DriverFactory {

    private static final Logger LOGGER =
            LogUtils.getLogger(DriverFactory.class);

    private DriverFactory() {
        // Utility class
    }

    public static AndroidDriver createAndroidDriver() {

        ExecutionMode executionMode =
                ConfigReader.getExecutionMode();

        LOGGER.info(
                "Execution mode: {}",
                executionMode
        );

        DriverProvider provider;

        switch (executionMode) {

            case LOCAL:
                provider = new LocalDriverProvider();
                break;

            case BROWSERSTACK:
                throw new FrameworkException(
                        "BrowserStack execution is not enabled yet."
                );

            default:
                throw new FrameworkException(
                        "Unsupported execution mode: "
                                + executionMode
                );
        }

        return provider.createDriver();
    }
}