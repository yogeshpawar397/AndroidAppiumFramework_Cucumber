package api;

import api.model.AuthRequest;
import api.model.AuthResponse;
import config.ConfigReader;
import constants.ApiEndpoints;
import exceptions.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthClient {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(AuthClient.class);

    private final ApiClient apiClient;

    public AuthClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public String generateToken(
            String userName,
            String password) {

        LOGGER.info("Generating API authentication token.");

        AuthRequest authRequest =
                new AuthRequest(
                        userName,
                        password
                );

        ApiRequest request =
                ApiRequestFactory.post(
                        ConfigReader.get(ApiEndpoints.AUTH),
                        authRequest
                );

        ApiResponse response =
                apiClient.execute(request);

        if (response.getStatusCode() != 200) {
            throw new ApiException(
                    "Authentication failed. Status code: "
                            + response.getStatusCode()
            );
        }

        AuthResponse authResponse =
                response.getBodyAs(AuthResponse.class);

        if (authResponse.getToken() == null
                || authResponse.getToken().isBlank()) {

            throw new ApiException(
                    "Authentication succeeded but token was not returned."
            );
        }

        return authResponse.getToken();
    }
}