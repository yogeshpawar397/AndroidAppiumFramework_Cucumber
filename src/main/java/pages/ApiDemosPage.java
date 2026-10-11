package pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiDemosPage extends BasePage {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ApiDemosPage.class);

    @AndroidFindBy(accessibility = "Accessibility")
    private WebElement accessibilityMenu;

    @AndroidFindBy(accessibility = "Accessibility Node Provider")
    private WebElement accessibilityNodeProvider;

    public ApiDemosPage() {
        PageFactory.initElements(
                new AppiumFieldDecorator(getDriver()),
                this
        );
    }

    public boolean isApiDemosDisplayed() {

        LOGGER.info(
                "Verifying ApiDemos application is displayed"
        );

        return accessibilityMenu != null
                && accessibilityMenu.isDisplayed();
    }

    public void clickAccessibilityMenu() {

        LOGGER.info(
                "Clicking Accessibility menu"
        );

        accessibilityMenu.click();
    }

    public boolean isAccessibilityNodeProviderDisplayed() {

        LOGGER.info(
                "Verifying Accessibility Node Provider option is displayed"
        );

        return accessibilityNodeProvider != null
                && accessibilityNodeProvider.isDisplayed();
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