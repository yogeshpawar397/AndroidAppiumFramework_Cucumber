package pages;

public class PageObjectManager {

    private DevicePage devicePage;
    private ApiDemosPage apiDemosPage;
    private FormPage formPage;

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
}