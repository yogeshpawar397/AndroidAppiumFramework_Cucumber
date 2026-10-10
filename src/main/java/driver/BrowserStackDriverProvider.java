package driver;

import exceptions.FrameworkException;
import io.appium.java_client.android.AndroidDriver;
import org.slf4j.Logger;
import utils.LogUtils;

public class BrowserStackDriverProvider
        implements DriverProvider {

    private static final Logger LOGGER =
            LogUtils.getLogger(
                    BrowserStackDriverProvider.class
            );

    @Override
    public AndroidDriver createDriver() {

        LOGGER.info(
                "BrowserStack execution selected. "
                        + "Driver creation is delegated to BrowserStack SDK."
        );

        throw new FrameworkException(
                "BrowserStack SDK driver integration is not enabled "
                        + "through DriverFactory yet."
        );
    }
}