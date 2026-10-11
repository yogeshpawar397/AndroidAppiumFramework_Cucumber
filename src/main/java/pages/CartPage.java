package pages;

import config.ConfigReader;
import exceptions.FrameworkException;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.ElementOption;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import utils.LogUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CartPage extends BasePage {

    private static final By PRODUCT_NAME_LOCATOR =
            By.id("com.androidsample.generalstore:id/productName");

    private static final By PRODUCT_PRICE_LOCATOR =
            By.id("com.androidsample.generalstore:id/productPrice");

    private static final Logger LOGGER =
            LogUtils.getLogger(CartPage.class);

    @AndroidFindBy(id = "com.androidsample.generalstore:id/totalAmountLbl")
    private WebElement totalAmount;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/termsButton")
    private WebElement terms;

    @AndroidFindBy(id = "android:id/button1")
    private WebElement acceptButton;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/btnProceed")
    private WebElement proceed;

    @AndroidFindBy(className = "android.widget.CheckBox")
    private WebElement checkBox;

    public CartPage() {
        PageFactory.initElements(
                new AppiumFieldDecorator(getDriver()),
                this
        );
    }

    public List<String> getProductList() {

        LOGGER.info("Retrieving product prices from cart");

        WebDriverWait wait = new WebDriverWait(
                getDriver(),
                Duration.ofSeconds(ConfigReader.getInt("explicitWait"))
        );
        wait.ignoring(StaleElementReferenceException.class);

        try {
            return wait.until(driver -> {
                List<WebElement> currentPrices =
                        driver.findElements(PRODUCT_PRICE_LOCATOR);

                if (currentPrices.isEmpty()) {
                    return null;
                }

                List<String> prices = new ArrayList<>();
                for (WebElement price : currentPrices) {
                    prices.add(price.getText());
                }

                return prices;
            });
        } catch (TimeoutException e) {
            throw new FrameworkException(
                    "No product prices became available in the cart.",
                    e
            );
        }
    }

    public boolean containsProduct(String productName) {

        if (productName == null || productName.isBlank()) {
            throw new FrameworkException(
                    "Product name must not be null or blank."
            );
        }

        LOGGER.info("Checking whether cart contains product '{}'", productName);

        WebDriverWait wait = new WebDriverWait(
                getDriver(),
                Duration.ofSeconds(ConfigReader.getInt("explicitWait"))
        );
        wait.ignoring(StaleElementReferenceException.class);

        try {
            return wait.until(driver -> driver
                    .findElements(PRODUCT_NAME_LOCATOR)
                    .stream()
                    .map(product -> product.getText().trim())
                    .anyMatch(name -> name.equalsIgnoreCase(productName)));
        } catch (TimeoutException e) {
            LOGGER.info("Product '{}' was not found in the cart", productName);
            return false;
        }
    }

    public double getProductsSum() {

        LOGGER.info("Calculating total of all product prices");

        double totalSum = 0.0;

        for (String amountString : getProductList()) {
            totalSum += getFormattedAmount(amountString);
        }

        return totalSum;
    }

    public Double getTotalAmountDisplayed() {

        LOGGER.info("Reading total amount displayed in cart");

        return getFormattedAmount(totalAmount.getText());
    }

    public void acceptTermsConditions() {

        LOGGER.info("Accepting terms and conditions");

        new TouchAction<>(getDriver())
                .longPress(LongPressOptions.longPressOptions()
                        .withElement(ElementOption.element(terms)))
                .release()
                .perform();

        acceptButton.click();
    }

    public void submitOrder() {

        LOGGER.info("Submitting cart order");

        checkBox.click();
        proceed.click();
    }

    public void completeOrder() {
//        acceptTermsConditions();
        submitOrder();
    }

    private double getFormattedAmount(String amountString) {

        if (amountString == null || amountString.isBlank()) {
            throw new FrameworkException(
                    "Amount must not be null or blank."
            );
        }

        String sanitized = amountString.replaceAll("[^0-9.]", "");

        if (sanitized.isBlank() || sanitized.equals(".")) {
            throw new FrameworkException(
                    "Amount could not be parsed: " + amountString
            );
        }

        return Double.parseDouble(sanitized);
    }
}
