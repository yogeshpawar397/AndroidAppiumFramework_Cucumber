package utils;

import api.ApiResponse;

import static org.testng.Assert.*;

public final class ApiAssertions {

    private ApiAssertions() {
    }

    public static void assertStatusCode(
            ApiResponse response,
            int expectedStatusCode) {

        assertEquals(
                response.getStatusCode(),
                expectedStatusCode,
                "Unexpected API response status code"
        );
    }

    public static void assertJsonFieldNotNull(
            ApiResponse response,
            String jsonPath) {

        Object value = response
                .getRawResponse()
                .jsonPath()
                .get(jsonPath);

        assertNotNull(
                value,
                "Expected JSON field was not found: " + jsonPath
        );
    }

    public static void assertNotEmpty(
            java.util.Collection<?> collection,
            String message) {

        assertNotNull(collection, message);
        assertFalse(collection.isEmpty(), message);
    }

    public static void assertJsonSchema(
            ApiResponse response,
            String schemaPath) {

        if (response == null) {
            throw new AssertionError(
                    "API response must not be null for schema validation."
            );
        }

        if (schemaPath == null || schemaPath.isBlank()) {
            throw new IllegalArgumentException(
                    "Schema path must not be null or blank."
            );
        }

        try {
            response.getRawResponse()
                    .then()
                    .assertThat()
                    .body(
                            io.restassured.module.jsv.JsonSchemaValidator
                                    .matchesJsonSchemaInClasspath(schemaPath)
                    );

        } catch (AssertionError e) {
            throw new AssertionError(
                    "JSON schema validation failed for schema: " + schemaPath,
                    e
            );
        }
    }
}