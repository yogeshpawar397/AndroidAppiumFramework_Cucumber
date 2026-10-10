package api.model;

public class AuthResponse {

    private String token;
    private String expires;
    private boolean status;
    private String result;

    public String getToken() {
        return token;
    }

    public String getExpires() {
        return expires;
    }

    public boolean isStatus() {
        return status;
    }

    public String getResult() {
        return result;
    }
}