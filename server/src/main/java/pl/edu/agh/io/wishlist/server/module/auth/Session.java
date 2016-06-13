package pl.edu.agh.io.wishlist.server.module.auth;

public class Session {

    int sessionId;

    String username;

    String userId;

    public int getSessionId() {
        return sessionId;
    }

    public String getUsername() {
        return username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
