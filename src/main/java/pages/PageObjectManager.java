package pages;

public class PageObjectManager {

    private SettingsPage settingsPage;
    private DevicePage devicePage;
    private ApiDemosPage apiDemosPage;

    public SettingsPage getSettingsPage() {

        if (settingsPage == null) {
            settingsPage = new SettingsPage();
        }

        return settingsPage;
    }

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
}