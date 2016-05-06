package pl.edu.agh.io.wishlist.android.auth;

import java.io.Serializable;

public class ServerCredentials implements Serializable {

    private String host;
    private int port;

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

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
