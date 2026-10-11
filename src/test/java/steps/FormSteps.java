package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.FormPage;
import pages.PageObjectManagerFactory;

public class FormSteps {

    private FormPage formPage() {
        return PageObjectManagerFactory.get().getFormPage();
    }

    @Given("I launch the General Store form")
    public void launchGeneralStoreForm() {
        formPage().setActivity();
    }

    @When("I enter the name {string}")
    public void enterName(String name) {
        formPage().setNameField(name);
    }

    @When("I select female gender")
    public void selectFemaleGender() {
        formPage().setGender("female");
    }

    @And("I select the country {string}")
    public void selectCountry(String countryName) {
        formPage().setCountrySelection(countryName);
    }

    @When("I submit the form")
    public void submitTheForm() {
        formPage().submitForm();
    }

    @When("I submit the form without entering a name")
    public void submitFormWithoutName() {
        formPage().submitForm();
    }

    @Then("the error toast {string} should be displayed")
    public void verifyErrorToast(String expectedMessage) {
        Assert.assertTrue(
                formPage().isToastDisplayed(expectedMessage),
                "Expected toast message '" + expectedMessage + "' was not displayed."
        );
    }

    @Then("the form should submit without an error toast")
    public void verifyPositiveFlow() {
        Assert.assertFalse(
                formPage().isToastDisplayed("Please your name"),
                "An error toast was displayed during the positive flow."
        );
    }
}
