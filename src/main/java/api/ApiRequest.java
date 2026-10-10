package api;

import java.util.HashMap;
import java.util.Map;
import constants.ApiHeaders;

public class ApiRequest {

    private String path;
    private HttpMethod method;

    private Map<String, String> headers =
            new HashMap<>();

    private Object body;

    public String getPath() {
        return path;
    }

    public ApiRequest setPath(String path) {
        this.path = path;
        return this;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public ApiRequest addHeader(
            String name,
            String value) {

        headers.put(name, value);
        return this;
    }

    public Object getBody() {
        return body;
    }

    public ApiRequest setBody(Object body) {

        this.body = body;
        return this;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public ApiRequest setMethod(HttpMethod method) {
        this.method = method;
        return this;
    }

    public void validate() {

        if (method == null) {
            throw new IllegalArgumentException(
                    "API request method must not be null."
            );
        }

        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException(
                    "API request path must not be null or blank."
            );
        }
    }

    public ApiRequest setJsonBody(Object body) {
        this.body = body;

        addHeader(
                ApiHeaders.CONTENT_TYPE,
                ApiHeaders.APPLICATION_JSON
        );

        return this;
    }
}