package pages;

import exceptions.FrameworkException;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import utils.LogUtils;

import java.util.List;

public class ProductCatalogue extends BasePage {

    private static final Logger LOGGER =
            LogUtils.getLogger(ProductCatalogue.class);

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='ADD TO CART']")
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

        if (index < 0 || index >= addToCart.size()) {
            throw new FrameworkException(
                    "Invalid product index: " + index
            );
        }

        LOGGER.info("Adding product at index {} to cart", index);

        addToCart.get(index).click();
    }

    public CartPage goToCartPage() {

        LOGGER.info("Navigating to cart page");

        cart.click();
        return new CartPage();
    }
}
