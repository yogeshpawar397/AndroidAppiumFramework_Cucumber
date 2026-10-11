package pages;

import exceptions.FrameworkException;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import utils.LogUtils;

import java.time.Duration;

public class FormPage extends BasePage {

    private static final Logger LOGGER =
            LogUtils.getLogger(FormPage.class);

    private static final String APP_PACKAGE =
            "com.androidsample.generalstore";

    private static final String MAIN_ACTIVITY =
            "com.androidsample.generalstore.MainActivity";

    private static final By TOAST_LOCATOR =
            By.xpath("(//android.widget.Toast)[1]");

    private static final Duration TOAST_WAIT =
            Duration.ofSeconds(2);

    @AndroidFindBy(id = "com.androidsample.generalstore:id/nameField")
    private WebElement nameField;

    @AndroidFindBy(xpath = "//android.widget.RadioButton[@text='Female']")
    private WebElement femaleOption;

    @AndroidFindBy(xpath = "//android.widget.RadioButton[@text='Male']")
    private WebElement maleOption;

    @AndroidFindBy(id = "android:id/text1")
    private WebElement countrySelection;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/btnLetsShop")
    private WebElement shopButton;

    public FormPage() {
        PageFactory.initElements(
                new AppiumFieldDecorator(getDriver()),
                this
        );
    }

    public void setNameField(String name) {

        if (name == null || name.isBlank()) {
            throw new FrameworkException(
                    "Name must not be null or blank."
            );
        }

        LOGGER.info("Entering customer name: {}", name);

        nameField.sendKeys(name);
        hideKeyboard();
    }

    public void setActivity() {

        LOGGER.info(
                "Activating General Store application package: {}",
                APP_PACKAGE
        );

        getDriver().activateApp(APP_PACKAGE);
    }

    public void setGender(String gender) {

        if (gender == null || gender.isBlank()) {
            throw new FrameworkException(
                    "Gender must not be null or blank."
            );
        }

        LOGGER.info("Selecting gender: {}", gender);

        if (gender.equalsIgnoreCase("female")) {
            femaleOption.click();
            return;
        }

        maleOption.click();
    }

    public void setCountrySelection(String countryName) {

        if (countryName == null || countryName.isBlank()) {
            throw new FrameworkException(
                    "Country name must not be null or blank."
            );
        }

        LOGGER.info("Selecting country: {}", countryName);

        countrySelection.click();

        WebElement countryOption = getDriver().findElement(
                io.appium.java_client.AppiumBy.androidUIAutomator(
                        String.format(
                                "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\"%s\").instance(0))",
                                countryName
                        )
                )
        );

        countryOption.click();
    }

    public void submitForm() {

        LOGGER.info("Submitting form");

        shopButton.click();
    }

    public String getToastMessage() {

        return waitForToastMessage();
    }

    private String waitForToastMessage() {

        try {
            return new WebDriverWait(getDriver(), TOAST_WAIT)
                    .pollingEvery(Duration.ofMillis(100))
                    .ignoring(StaleElementReferenceException.class)
                    .until(driver -> {
                        WebElement toast = driver.findElements(TOAST_LOCATOR)
                                .stream()
                                .findFirst()
                                .orElse(null);

                        if (toast == null) {
                            return null;
                        }

                        String message = toast.getAttribute("name");
                        return message == null || message.isBlank()
                                ? null
                                : message;
                    });
        } catch (TimeoutException e) {
            return "";
        }
    }

    public boolean isToastDisplayed(String expectedMessage) {

        String toastMessage = getToastMessage();

        if (toastMessage == null || toastMessage.isBlank()) {
            return false;
        }

        return toastMessage.equals(expectedMessage);
    }
}
