package pages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DevicePage extends BasePage {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(DevicePage.class);

    public String getDeviceTime() {

        LOGGER.info("Retrieving device time");

        return getDriver().getDeviceTime();
    }

    public String getCurrentPackage() {

        LOGGER.info("Retrieving current Android package");

        return getDriver().getCurrentPackage();
    }
}