
package api;

import config.ConfigReader;
import constants.ApiHeaders;
import exceptions.ApiException;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiClient {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ApiClient.class);

    public ApiResponse execute(ApiRequest request) {

        if (request == null) {
            throw new IllegalArgumentException(
                    "API request must not be null.");
        }

        request.validate();

        LOGGER.info(
                "Sending {} request: {}",
                request.getMethod(),
                request.getPath());

        try {
            RequestSpecification specification =
                    buildSpecification(request);

            boolean hasAuthorizationHeader =
                    request.getHeaders()
                            .keySet()
                            .stream()
                            .anyMatch(header ->
                                    ApiHeaders.AUTHORIZATION
                                            .equalsIgnoreCase(header));

            if (!hasAuthorizationHeader
                    && AuthTokenManager.hasToken()) {
                specification.header(
                        ApiHeaders.AUTHORIZATION,
                        ApiHeaders.BEARER
                                + AuthTokenManager.getToken());
            }

            ApiResponse response = switch (request.getMethod()) {
                case GET -> new ApiResponse(
                        specification.get(request.getPath()));
                case POST -> new ApiResponse(
                        specification.post(request.getPath()));
                case PUT -> new ApiResponse(
                        specification.put(request.getPath()));
                case PATCH -> new ApiResponse(
                        specification.patch(request.getPath()));
                case DELETE -> new ApiResponse(
                        specification.delete(request.getPath()));
            };

            LOGGER.info(
                    "API response received. Status code: {}",
                    response.getStatusCode());

            return response;

        } catch (Exception e) {
            LOGGER.error(
                    "API request failed. Method: {}, Path: {}",
                    request.getMethod(),
                    request.getPath(),
                    e);

            throw new ApiException(
                    String.format(
                            "API request failed. Method: %s, Path: %s",
                            request.getMethod(),
                            request.getPath()),
                    e);
        }
    }

    private RequestSpecification buildSpecification(
            ApiRequest request) {

        RequestSpecification specification =
                RestAssured.given()
                        .baseUri(ConfigReader.get("apiBaseUrl"))
                        .header(
                                ApiHeaders.ACCEPT,
                                ApiHeaders.APPLICATION_JSON);

        if (request.getBody() != null) {
            specification.header(
                    ApiHeaders.CONTENT_TYPE,
                    ApiHeaders.APPLICATION_JSON);

            specification.body(request.getBody());
        }

        if (!request.getHeaders().isEmpty()) {
            specification.headers(request.getHeaders());
        }

        return specification;
    }
}
