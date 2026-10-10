package steps;

import api.ApiClient;
import api.ApiRequest;
import api.ApiRequestFactory;
import api.ApiResponse;
import api.AuthClient;
import api.AuthTokenManager;
import api.HttpMethod;

import api.model.AddBookRequest;
import api.model.BooksResponse;

import config.ConfigReader;

import constants.ApiConstants;
import constants.ApiEndpoints;

import context.ScenarioContextFactory;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import utils.ApiAssertions;

public class ApiSteps {

    private final ApiClient apiClient = new ApiClient();

    private void storeApiResponse(String key, ApiResponse response) {
        ScenarioContextFactory
                .get()
                .set(key, response);
    }

    private ApiResponse getApiResponse(String key) {

        return ScenarioContextFactory
                .get()
                .get(key, ApiResponse.class);
    }

    @When("I send a GET request to the books endpoint")
    public void sendGetRequestToBooksEndpoint() {

        ApiRequest request =
                ApiRequestFactory.get(
                        ConfigReader.get(ApiEndpoints.BOOKS)
                );

        ApiResponse response =
                apiClient.execute(request);

        storeApiResponse(
                ApiConstants.API_RESPONSE,
                response
        );
    }

    @Then("the API response status code should be {int}")
    public void verifyApiResponseStatusCode(int expectedStatusCode) {

        ApiResponse response =
                getApiResponse(ApiConstants.API_RESPONSE);

        ApiAssertions.assertStatusCode(
                response,
                expectedStatusCode
        );
    }

    @Then("the API response should contain the books")
    public void verifyApiResponseContainsBooks() {

        ApiResponse response =
                getApiResponse(ApiConstants.API_RESPONSE);

        BooksResponse booksResponse =
                response.getBodyAs(BooksResponse.class);

        ApiAssertions.assertNotEmpty(
                booksResponse.getBooks(),
                "Expected books list should not be empty"
        );
    }

    @When("I send a POST request to add a book")
    public void sendPostRequestToAddBook() {

        AddBookRequest requestBody =
                new AddBookRequest(
                        "some-user-id",
                        "9781449325862"
                );

        ApiRequest request =
                new ApiRequest()
                        .setMethod(HttpMethod.POST)
                        .setPath(
                                ConfigReader.get(
                                        ApiEndpoints.ADD_BOOK
                                )
                        )
                        .setJsonBody(requestBody);

        ApiResponse response =
                apiClient.execute(request);

        storeApiResponse(
                ApiConstants.API_RESPONSE,
                response
        );
    }

    @When("I authenticate with the BookStore API")
    public void authenticateWithBookStoreApi() {

        String userName =
                ConfigReader.getEnvironmentVariable(
                        "BOOKSTORE_USERNAME"
                );

        String password =
                ConfigReader.getEnvironmentVariable(
                        "BOOKSTORE_PASSWORD"
                );

        AuthClient authClient =
                new AuthClient(apiClient);

        String token =
                authClient.generateToken(
                        userName,
                        password
                );

        AuthTokenManager.setToken(token);
    }

    @And("the API response should match the books schema")
    public void theApiResponseShouldMatchTheBooksSchema() {

        ApiAssertions.assertJsonSchema(
                getApiResponse(ApiConstants.API_RESPONSE),
                "schemas/books-response-schema.json"
        );;
    }

    @When("I send a GET request to the books endpoint and store the response as setup response")
    public void sendGetRequestAndStoreAsSetupResponse() {

        ApiRequest request =
                ApiRequestFactory.get(
                        ConfigReader.get(ApiEndpoints.BOOKS)
                );

        ApiResponse response =
                apiClient.execute(request);

        storeApiResponse(
                ApiConstants.SETUP_API_RESPONSE,
                response
        );
    }

    @Then("the setup API response status code should be {int}")
    public void verifySetupApiResponseStatusCode(int expectedStatusCode) {

        ApiResponse response =
                getApiResponse(ApiConstants.SETUP_API_RESPONSE);

        ApiAssertions.assertStatusCode(
                response,
                expectedStatusCode
        );
    }

    @When("I send a GET request to the books endpoint and store the response as verification response")
    public void sendGetRequestAndStoreAsVerificationResponse() {

        ApiRequest request =
                ApiRequestFactory.get(
                        ConfigReader.get(ApiEndpoints.BOOKS)
                );

        ApiResponse response =
                apiClient.execute(request);

        storeApiResponse(
                ApiConstants.VERIFICATION_API_RESPONSE,
                response
        );
    }

    @Then("the verification API response status code should be {int}")
    public void verifyVerificationApiResponseStatusCode(int expectedStatusCode) {

        ApiResponse response =
                getApiResponse(ApiConstants.VERIFICATION_API_RESPONSE);

        ApiAssertions.assertStatusCode(
                response,
                expectedStatusCode
        );
    }
}