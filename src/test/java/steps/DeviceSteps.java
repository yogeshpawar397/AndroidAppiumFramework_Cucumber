package steps;

import config.ConfigReader;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.PageObjectManagerFactory;

public class DeviceSteps {

    @Then("the Android device should be connected")
    public void verifyAndroidDeviceIsConnected() {

        String deviceTime =
                PageObjectManagerFactory
                        .get()
                        .getDevicePage()
                        .getDeviceTime();

        System.out.println("Device Time: " + deviceTime);
    }

    @Then("the current Android package should be available")
    public void verifyCurrentAndroidPackage() {

        String currentPackage =
                PageObjectManagerFactory
                        .get()
                        .getDevicePage()
                        .getCurrentPackage();

        String expectedPackage =
                ConfigReader.get("appPackage");

        Assert.assertEquals(
                currentPackage,
                expectedPackage,
                "Unexpected Android package"
        );
    }
}