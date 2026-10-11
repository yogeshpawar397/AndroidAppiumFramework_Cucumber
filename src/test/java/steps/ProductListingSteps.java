package steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.PageObjectManagerFactory;

public class ProductListingSteps {

    @When("I scroll down until the product {string} is visible")
    public void scrollToProduct(String productName) {
        PageObjectManagerFactory
                .get()
                .getProductCatalogue()
                .scrollToProduct(productName);
    }

    @When("I add the product {string} to the cart")
    public void addProductToCart(String productName) {
        PageObjectManagerFactory
                .get()
                .getProductCatalogue()
                .addItemToCartByName(productName);
    }

    @When("I add the first {int} products to the cart")
    public void addFirstProductsToCart(int numberOfProducts) {
        PageObjectManagerFactory
                .get()
                .getProductCatalogue()
                .addFirstProductsToCart(numberOfProducts);
    }

    @When("I open the shopping cart")
    public void openShoppingCart() {
        PageObjectManagerFactory
                .get()
                .getProductCatalogue()
                .goToCartPage();
    }

    @Then("the shopping cart should contain the product {string}")
    public void verifyProductInCart(String productName) {
        Assert.assertTrue(
                PageObjectManagerFactory
                        .get()
                        .getCartPage()
                        .containsProduct(productName),
                "Shopping cart does not contain product: " + productName
        );
    }

    @Then("the total purchase amount should equal the sum of the product prices")
    public void verifyTotalPurchaseAmount() {
        double productsSum = PageObjectManagerFactory
                .get()
                .getCartPage()
                .getProductsSum();
        double displayedTotal = PageObjectManagerFactory
                .get()
                .getCartPage()
                .getTotalAmountDisplayed();

        Assert.assertEquals(
                displayedTotal,
                productsSum,
                0.001,
                "Displayed total purchase amount does not match the sum "
                        + "of the product prices."
        );
    }

    @When("I complete the shopping cart order")
    public void completeShoppingCartOrder() {
        PageObjectManagerFactory
                        .get()
                        .getCartPage()
                        .completeOrder();
    }

    @When("I switch to the web browser page")
    public void switchToWebBrowserPage() {
        PageObjectManagerFactory
                        .get()
                        .getWebBrowserPage()
                        .switchToWebBrowser();
    }

    @When("I search the web browser for {string}")
    public void searchWebBrowser(String query) {
        PageObjectManagerFactory
                        .get()
                        .getWebBrowserPage()
                        .search(query);
    }

    @When("I return to the native app")
    public void returnToNativeApp() {
        PageObjectManagerFactory
                        .get()
                        .getWebBrowserPage()
                        .returnToNativeApp();
    }
}
