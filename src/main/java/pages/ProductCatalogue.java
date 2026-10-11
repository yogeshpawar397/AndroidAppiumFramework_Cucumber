package pages;

import exceptions.FrameworkException;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import utils.LogUtils;

import java.util.List;

public class ProductCatalogue extends BasePage {

    private static final Logger LOGGER =
            LogUtils.getLogger(ProductCatalogue.class);

    @AndroidFindBy(id = "com.androidsample.generalstore:id/productName")
    private List<WebElement> productNames;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/productAddCart")
    private List<WebElement> addToCart;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/appbar_btn_cart")
    private WebElement cart;

    public ProductCatalogue() {
        PageFactory.initElements(
                new AppiumFieldDecorator(getDriver()),
                this
        );
    }

    public void addItemToCartByIndex(int index) {

        if (index < 0
                || index >= productNames.size()
                || index >= addToCart.size()) {
            throw new FrameworkException(
                    "Invalid product index: " + index
            );
        }

        LOGGER.info("Adding product at index {} to cart", index);

        addToCart.get(index).click();
    }

    public void addFirstProductsToCart(int numberOfProducts) {

        if (numberOfProducts < 1) {
            throw new FrameworkException(
                    "Number of products must be at least one."
            );
        }

        if (numberOfProducts > productNames.size()
                || numberOfProducts > addToCart.size()) {
            throw new FrameworkException(
                    "Cannot add " + numberOfProducts
                            + " products; fewer products are available."
            );
        }

        LOGGER.info(
                "Adding the first {} products to the cart",
                numberOfProducts
        );

        for (int index = 0; index < numberOfProducts; index++) {
            addItemToCartByIndex(index);
        }
    }

    public void addItemToCartByName(String productName) {

        if (productName == null || productName.isBlank()) {
            throw new FrameworkException(
                    "Product name must not be null or blank."
            );
        }

        scrollToProduct(productName);

        for (int index = 0; index < productNames.size(); index++) {
            if (productName.equalsIgnoreCase(
                    productNames.get(index).getText().trim())) {
                if (index >= addToCart.size()) {
                    throw new FrameworkException(
                            "No add-to-cart button found for product: "
                                    + productName
                    );
                }

                LOGGER.info("Adding product '{}' to cart", productName);
                addToCart.get(index).click();
                return;
            }
        }

        throw new FrameworkException(
                "Product was not found in the catalogue: " + productName
        );
    }

    public void scrollToProduct(String productName) {

        if (productName == null || productName.isBlank()) {
            throw new FrameworkException(
                    "Product name must not be null or blank."
            );
        }

        LOGGER.info("Scrolling product catalogue to '{}'", productName);

        By productLocator = AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))"
                        + ".scrollIntoView(new UiSelector().text(\""
                        + escapeUiAutomatorText(productName)
                        + "\"))"
        );

        if (getDriver().findElements(productLocator).isEmpty()) {
            throw new FrameworkException(
                    "Product was not found in the catalogue: " + productName
            );
        }
    }

    private String escapeUiAutomatorText(String text) {
        return text.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    public CartPage goToCartPage() {

        LOGGER.info("Navigating to cart page");

        cart.click();
        return new CartPage();
    }
}
