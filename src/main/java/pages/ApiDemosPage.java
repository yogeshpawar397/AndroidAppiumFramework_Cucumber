package pages;

import io.appium.java_client.AppiumBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiDemosPage extends BasePage {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ApiDemosPage.class);

    private static final String ACCESSIBILITY =
            "Accessibility";

    private static final String ACCESSIBILITY_NODE_PROVIDER =
            "Accessibility Node Provider";

    public boolean isApiDemosDisplayed() {

        LOGGER.info(
                "Verifying ApiDemos application is displayed"
        );

        return isDisplayed(
                AppiumBy.accessibilityId(
                        ACCESSIBILITY
                )
        );
    }

    public void clickAccessibilityMenu() {

        LOGGER.info(
                "Clicking Accessibility menu"
        );

        click(
                AppiumBy.accessibilityId(
                        ACCESSIBILITY
                )
        );
    }

    
    public boolean isAccessibilityNodeProviderDisplayed() {

        LOGGER.info(
                "Verifying Accessibility Node Provider option is displayed"
        );

        return isDisplayed(
                AppiumBy.accessibilityId(
                        ACCESSIBILITY_NODE_PROVIDER
                )
        );
    }

    public void logCurrentAppState() {

        LOGGER.info("Logging current Android application state");

        String currentPackage = getDriver().getCurrentPackage();
        String currentActivity = getDriver().currentActivity();

        LOGGER.info(
                "Current Android app state - Package: {}, Activity: {}",
                currentPackage,
                currentActivity
        );
    }

}