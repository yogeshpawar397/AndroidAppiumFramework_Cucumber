package driver;

import io.appium.java_client.android.AndroidDriver;

public interface DriverProvider {

    AndroidDriver createDriver();
}