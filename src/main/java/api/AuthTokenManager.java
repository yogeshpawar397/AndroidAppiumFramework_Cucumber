package api;

import context.ScenarioContextFactory;
import constants.AuthConstants;

public final class AuthTokenManager {

    private AuthTokenManager() {
    }

    public static void setToken(String token) {

        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException(
                    "Authentication token must not be null or blank."
            );
        }

        ScenarioContextFactory
                .get()
                .set(
                        AuthConstants.AUTH_TOKEN,
                        token
                );
    }

    public static String getToken() {

        if (!ScenarioContextFactory.isInitialized()) {
            return null;
        }

        if (!ScenarioContextFactory
                .get()
                .contains(AuthConstants.AUTH_TOKEN)) {
            return null;
        }

        return ScenarioContextFactory
                .get()
                .get(
                        AuthConstants.AUTH_TOKEN,
                        String.class
                );
    }

    public static boolean hasToken() {
        String token = getToken();
        return token != null && !token.isBlank();
    }

    public static void clearToken() {

        if (ScenarioContextFactory.isInitialized()) {
            ScenarioContextFactory
                    .get()
                    .remove(AuthConstants.AUTH_TOKEN);
        }
    }
}