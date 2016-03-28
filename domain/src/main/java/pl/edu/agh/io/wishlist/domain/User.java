package pl.edu.agh.io.wishlist.domain;

import org.springframework.data.annotation.Id;

import java.util.List;

public class User {

    @Id
    private long id;

    private String login;
    private String password;
    private List<Long> friends;

    public User(String login, String password) {
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

    public List<Long> getFriends() {
        return friends;
    }

    public void setFriends(List<Long> friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", friends=" + friends +
                '}';
    }
}

