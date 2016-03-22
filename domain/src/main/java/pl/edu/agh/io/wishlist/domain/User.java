package pl.edu.agh.io.wishlist.domain;

public class User {
    private final long id;
    private final String login;
    private final String password;

    public User(long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}

