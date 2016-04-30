package pl.edu.agh.io.wishlist.android;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpHeaders;

public class ServerCredentials {

    private String host;
    private int port;

    private HttpHeaders httpHeaders;

    public ServerCredentials(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public ServerCredentials() {}

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUrl() {
        return host + ":" + port;
    }

    public String getUrl(String path) {
        return getUrl() + "/" + path;
    }

    public void authenticate(String username, String password) {
        // Populate the HTTP Basic Authentication header with the username and password
        HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
        httpHeaders = new HttpHeaders();
        httpHeaders.setAuthorization(authHeader);
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }
}
