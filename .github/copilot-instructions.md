# Copilot Instructions for Android Appium Framework

## Project overview
This repository is an Android-only Appium automation framework built with Java 21, Maven, Selenium, Appium Java Client, TestNG, and Cucumber. The framework currently uses:

- Appium Java Client 10.1.1
- Selenium Java 4.42.0
- TestNG 7.9.0
- Cucumber 7.34.8
- SLF4J for logging
- Allure for reporting
- RestAssured for API automation

The existing code is structured around a clear separation of responsibilities. Preserve this design unless a change is explicitly required.

## Existing project structure
Keep the current package and folder layout intact.

- `src/main/java`
  - `config` — configuration loading and property access
  - `constants` — framework and environment constants
  - `driver` — Appium driver creation and provider classes
  - `pages` — page object classes
  - `utils` — reusable Selenium/Appium helpers
  - `api` — API client and auth helpers
  - `context` — ScenarioContext storage
- `src/test/java`
  - `base` — test setup/teardown base classes
  - `hooks` — Cucumber lifecycle hooks
  - `runners` — test runner configuration
  - `steps` — step definitions
  - `utils` — failure/reporting helpers
- `src/test/resources`
  - `features` — Gherkin scenarios
  - `schemas` — API schema validation artifacts if added
  - `testdata` — JSON data files used by tests
- `src/main/resources`
  - `common.properties`
  - `config-local.properties`
  - `config-qa.properties`
  - `config.properties`

Do not reorganize packages or rename existing classes without a strong reason and a review of all references.

## Required implementation rules
1. Preserve the existing folder structure and naming conventions.
   - Use the same package names and class naming patterns already in the project.
   - Add new classes in the correct package, not next to unrelated code.

2. Reuse existing classes and utilities before creating a duplicate.
   - Prefer `ConfigReader`, `DriverManager`, `WaitUtils`, `ElementUtils`, `ScreenshotUtils`, `LogUtils`, and page object classes already in use.
   - If a behavior already exists in the framework, extend it instead of creating a parallel implementation.

3. Follow the Page Object Model.
   - Keep UI interactions in page objects under `src/main/java/pages`.
   - Step definitions under `src/test/java/steps` should orchestrate actions and assertions, not contain low-level element logic.
   - Keep locators and interaction methods inside page object classes or shared utility methods, not directly in feature steps.

4. Maintain separation of concerns.
   - Tests/steps belong in `src/test/java`.
   - Core app logic belongs in `src/main/java`.
   - Driver setup lives in `driver` package.
   - Configuration lives in `config` and `constants`.
   - Shared helpers live in `utils`.
   - Reporting/failure handling stays in the test utilities layer.

5. Use Java, Maven, TestNG, Selenium, and Appium according to the existing dependencies.
   - Keep Java 21 compatibility.
   - Use the existing Appium Java Client, Selenium, TestNG, and Cucumber versions already defined in `pom.xml`.
   - Do not introduce new automation frameworks or replace the current stack without explicit approval.

6. Respect the existing `DriverManager` and `ThreadLocal` pattern for parallel execution.
   - `DriverManager` stores the current thread’s `AndroidDriver` in a `ThreadLocal`.
   - All driver access must go through `DriverManager.getDriver()` or the related utility patterns.
   - Do not create a second global driver or duplicate ThreadLocal storage.
   - Ensure any new parallel-safe behavior follows the same per-thread model.

7. Read configuration from the existing configuration mechanism.
   - Use `ConfigReader` and the properties files in `src/main/resources`.
   - `ConfigReader` loads `common.properties` and `config-<environment>.properties` based on the `environment` system property or default of `local`.
   - Do not hardcode credentials, URLs, device names, app names, app package names, app activities, or environment-specific values directly in tests or page objects.
   - If a value is required, read it from `ConfigReader` or from an environment variable in the existing way.

8. Follow existing logging, exception handling, screenshot, and reporting conventions.
   - Use SLF4J logger patterns already used throughout the project.
   - Reuse `FrameworkException` for framework-level failures and `ApiException` for API failures.
   - Use `FailureHandler` to capture screenshots on failed scenarios and attach them to the Cucumber `Scenario`.
   - Keep logs clear and useful; do not add noisy or duplicate logs in unrelated code.
   - Use existing `Before`/`After` hook patterns in `CucumberHooks` for setup and teardown.

9. Before modifying code, inspect related classes and identify dependencies.
   - Read the relevant classes before editing.
   - Check dependent page objects, drivers, hooks, steps, utilities, and config entries that might be affected.
   - If a change affects a shared utility, review all call sites before making the edit.
   - Avoid breaking the existing initialization flow: `ConfigReader -> DriverManager -> PageObjectManagerFactory -> step execution -> teardown`.

10. Avoid unnecessary changes to working code and do not remove existing functionality.
   - Do not refactor unrelated logic.
   - Do not delete or disable existing page objects, configurations, or step definitions unless required by the task.
   - Keep methods and behavior stable unless the task explicitly requires a change.

11. Follow the current Appium test flow.
   - Driver creation happens in `DriverFactory` through the active `DriverProvider` and requires Appium server configuration from `ConfigReader`.
   - Local execution is the default flow (`ExecutionMode.LOCAL` in `ConfigReader`).
   - `CapabilityManager` builds `UiAutomator2Options` and validates app and package settings.
   - `LocalDriverProvider` creates the session and activates the configured app when applicable.
   - Respect the expected app activation and Android package flow used by this framework.

12. Preserve existing Cucumber and TestNG usage.
   - Use the existing runner pattern in `src/test/java/runners/RunCucumberTest.java`.
   - Keep feature files in `src/test/resources/features` and use the existing step definitions and hook lifecycle.
   - Do not replace Cucumber/TestNG with a different test framework unless explicitly requested.

13. Respect current API and scenario-context patterns.
   - API logic under `src/main/java/api` should remain consistent with the existing `ApiClient`, `ApiRequest`, `ApiResponse`, and token manager patterns.
   - Scenario state uses `ScenarioContextFactory` and `ScenarioContext` with per-thread storage; reuse this instead of introducing ad hoc global state.

14. Keep reporting and test evidence intact.
   - Cucumber HTML/JSON reports are configured in `RunCucumberTest`.
   - Allure reporting is enabled through the Cucumber and TestNG integration.
   - Failure screenshots are attached through `FailureHandler` when a scenario fails.
   - Preserve that reporting flow when modifying tests or hooks.

15. After changes, run Maven compilation and relevant tests where possible, and report any failures.
   - Use the smallest valid Maven command that checks the changed behavior.
   - Prefer focused validation over broad suite execution.
   - Example checks include:
     - `mvn -q -DskipTests compile`
     - `mvn test -Dcucumber.filter.tags="@smoke"`
     - `mvn clean test`
   - Never claim tests passed unless they were actually executed successfully.
   - If a relevant test or compile step fails, report the failure clearly and do not say the fix is verified.

## Core coding expectations
- Prefer small, targeted edits over wide rewrites.
- Reuse existing assertions and test patterns (`Assert.assertTrue`, `Assert.assertEquals`, etc.) already used in step definitions.
- Keep names and method signatures consistent with the current code style.
- Follow the project’s existing patterns for logging, exception wrappers, and page-object methods.
- When editing a page object or utility, review dependent step definitions and hooks before finalizing the change.

## Example of the intended flow
A typical change should follow this pattern:

1. Inspect the page object, utils, and config that the new test will depend on.
2. Reuse `ConfigReader` and `DriverManager`.
3. Add or extend the relevant page object under `src/main/java/pages`.
4. Add or adjust the step definition under `src/test/java/steps`.
5. Ensure the feature file and hook behavior remain consistent.
6. Validate with the smallest Maven compile/test command that exercises the change.
7. Report real results, including failures.

Do not introduce hidden globals, duplicate configuration loaders, duplicate driver initialization, or custom wrappers that bypass the framework’s existing patterns.
