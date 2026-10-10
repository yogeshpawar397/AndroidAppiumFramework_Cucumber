package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.PageObjectManagerFactory;

public class ApiDemosSteps {

    @Then("the ApiDemos application should be displayed")
    public void verifyApiDemosApplicationIsDisplayed() {

        boolean displayed =
                PageObjectManagerFactory
                        .get()
                        .getApiDemosPage()
                        .isApiDemosDisplayed();

        Assert.assertTrue(
                displayed,
                "ApiDemos application is not displayed"
        );
    }

    @When("I click on the Accessibility menu")
    public void clickAccessibilityMenu() {

        PageObjectManagerFactory
                .get()
                .getApiDemosPage()
                .clickAccessibilityMenu();
    }

    @And("the Accessibility Node Provider option should be displayed")
    public void verifyAccessibilityNodeProviderDisplayed() {

        boolean displayed =
                PageObjectManagerFactory
                        .get()
                        .getApiDemosPage()
                        .isAccessibilityNodeProviderDisplayed();

        Assert.assertTrue(
                displayed,
                "Accessibility Node Provider option is not displayed"
        );
    }

    @When("I log the current Android app state")
    public void logCurrentAndroidAppState() {
        PageObjectManagerFactory
                .get()
                .getApiDemosPage()
                .logCurrentAppState();
    }

}
