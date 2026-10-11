# Android Appium Framework

Android-only Appium + Java + Maven + TestNG + Cucumber BDD framework.

## Prerequisites

- Java 21
- Maven 3.8+
- Node.js + Appium 3.x
- Appium UiAutomator2 driver
- Android SDK with `adb`
- Android device/emulator connected and authorized

## Current environment

This project is configured for the existing setup:

- Android: Vivo T4 5G (`V2502`)
- Automation: UiAutomator2
- Appium server: `http://127.0.0.1:4723`
- Java: 21

## Run

1. Stop any existing Appium server, then start it with automatic compatible
   ChromeDriver downloads enabled:

   `.\tools\start-appium-webview.ps1`

2. Verify the device:

   `adb devices`

3. From the project root run:

   `mvn clean test`

The first run downloads Maven dependencies.

## Maven repository on F:

To avoid consuming C: space, use a Maven local repository on F:, for example:

`mvn -Dmaven.repo.local=F:\\MavenRepo clean test`

You can later make this permanent with a Maven `settings.xml`.

## Configuration

Edit `src/main/resources/config.properties`.

For the initial framework smoke test, `autoLaunch=false` and app details are blank. This lets Appium create a session without assuming a specific application under test.

For a real APK, populate for example:

```properties
app=F:/Apps/MyApp.apk
appPackage=com.example.myapp
appActivity=.MainActivity
autoLaunch=true
```

If only one device is connected, leave `udid` blank. When multiple devices are connected, set the exact device UDID.

For Android WebView automation, UiAutomator2 detects the WebView's Chrome version
and downloads a compatible ChromeDriver into the configured
`chromeDriverExecutableDir` (`target/chromedrivers` by default). The Appium
server must be started with `uiautomator2:chromedriver_autodownload` allowed,
as shown above. This is a server startup setting; setting the executable
directory capability alone does not enable downloads. Confirm the Appium
startup log shows the insecure feature is allowed. The launcher checks port
4723 and refuses to start if another process is already listening; stop that
server before rerunning the launcher. To use a preinstalled driver instead, set
`chromeDriverExecutable` to its path; that path must be accessible to the
Appium server.

## Cucumber / BDD

Cucumber is integrated with TestNG using `cucumber-java` and `cucumber-testng`. The framework contains a working feature, step definitions, hooks, and a TestNG Cucumber runner.

Cucumber version: `7.34.8`. Cucumber-JVM supports TestNG execution and parallel execution; this framework keeps parallel execution disabled initially until the driver/test-data isolation is configured for your project.

Run all TestNG + Cucumber tests:

`mvn clean test`

Run only Cucumber scenarios tagged `@smoke`:

`mvn test -Dcucumber.filter.tags="@smoke"`

The Cucumber HTML and JSON reports are generated under:

`target/cucumber-reports/`

## Allure report

After running tests, serve the Allure report with the configured Maven plugin:

`mvn allure:serve`

The plugin reads results from `target/allure-results` and opens the generated
report in a browser. The standalone `allure` CLI is not required.

## Structure

```text
src/main/java
├── config
├── constants
├── driver
├── pages
└── utils

src/test/java
├── base
├── hooks
├── runners
├── steps
└── tests

src/test/resources
└── features
    └── device_connection.feature
```

### Cucumber execution flow

```text
Feature file
    ↓
RunCucumberTest
    ↓
CucumberHooks @Before
    ↓
DriverManager
    ↓
Step Definitions
    ↓
CucumberHooks @After
    ↓
Cucumber HTML/JSON report
```

The framework is intentionally a clean foundation. Page Objects, scenario-specific hooks, screenshots on failure, Allure/Extent reporting, retry logic, parallel execution, API utilities, test data, and CI/CD can be added as separate layers.
