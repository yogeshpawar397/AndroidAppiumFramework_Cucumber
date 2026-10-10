package api;

import constants.ApiHeaders;

public final class ApiRequestFactory {

    private ApiRequestFactory() {
    }

    public static ApiRequest get(String path) {

        return new ApiRequest()
                .setMethod(HttpMethod.GET)
                .setPath(path)
                .addHeader(
                        ApiHeaders.ACCEPT,
                        ApiHeaders.APPLICATION_JSON
                );
    }

    public static ApiRequest post(
            String path,
            Object body) {

        return new ApiRequest()
                .setMethod(HttpMethod.POST)
                .setPath(path)
                .setJsonBody(body);
    }

    public static ApiRequest put(
            String path,
            Object body) {

        return new ApiRequest()
                .setMethod(HttpMethod.PUT)
                .setPath(path)
                .setJsonBody(body);
    }

    public static ApiRequest patch(
            String path,
            Object body) {

        return new ApiRequest()
                .setMethod(HttpMethod.PATCH)
                .setPath(path)
                .setJsonBody(body);
    }

    public static ApiRequest delete(String path) {

        return new ApiRequest()
                .setMethod(HttpMethod.DELETE)
                .setPath(path)
                .addHeader(
                        ApiHeaders.ACCEPT,
                        ApiHeaders.APPLICATION_JSON
                );
    }
}