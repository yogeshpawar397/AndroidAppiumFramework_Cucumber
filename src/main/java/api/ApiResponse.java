package api;

import exceptions.ApiException;
import io.restassured.response.Response;

public class ApiResponse {

    private final Response response;

    public ApiResponse(Response response) {
        this.response = response;
    }

    public int getStatusCode() {
        return response.getStatusCode();
    }

    public String getBody() {
        return response.getBody().asString();
    }

    public String getHeader(String name) {
        return response.getHeader(name);
    }

    public <T> T getBodyAs(Class<T> type) {

        try {
            return response.as(type);

        } catch (Exception e) {

            throw new ApiException(
                    "Failed to deserialize API response to: "
                            + type.getSimpleName(),
                    e
            );
        }
    }

    public Response getRawResponse() {
        return response;
    }

    public boolean isSuccessful() {
        return response.getStatusCode() >= 200
                && response.getStatusCode() < 300;
    }

    public boolean isClientError() {
        return response.getStatusCode() >= 400
                && response.getStatusCode() < 500;
    }

    public boolean isServerError() {
        return response.getStatusCode() >= 500;
    }
}