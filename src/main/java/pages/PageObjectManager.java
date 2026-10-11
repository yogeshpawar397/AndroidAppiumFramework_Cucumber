package pages;

public class PageObjectManager {

    private DevicePage devicePage;
    private ApiDemosPage apiDemosPage;
    private FormPage formPage;
    private ProductCatalogue productCatalogue;
    private CartPage cartPage;
    private WebBrowserPage webBrowserPage;

    public DevicePage getDevicePage() {

        if (devicePage == null) {
            devicePage = new DevicePage();
        }

        return devicePage;
    }

    public ApiDemosPage getApiDemosPage() {

        if (apiDemosPage == null) {
            apiDemosPage = new ApiDemosPage();
        }

        return apiDemosPage;
    }

    public FormPage getFormPage() {

        if (formPage == null) {
            formPage = new FormPage();
        }

        return formPage;
    }

    public ProductCatalogue getProductCatalogue() {

        if (productCatalogue == null) {
            productCatalogue = new ProductCatalogue();
        }

        return productCatalogue;
    }

    public CartPage getCartPage() {

        if (cartPage == null) {
            cartPage = new CartPage();
        }

        return cartPage;
    }

    public WebBrowserPage getWebBrowserPage() {

        if (webBrowserPage == null) {
            webBrowserPage = new WebBrowserPage();
        }

        return webBrowserPage;
    }
}