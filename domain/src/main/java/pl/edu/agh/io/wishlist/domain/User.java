package pl.edu.agh.io.wishlist.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.List;

public class User {

    @Id
    private ObjectId id;

    private String login;
    private String password;

    private List<Long> friends;
    private List<Long> gifts;

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public ObjectId getId() {
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

    public List<Long> getGifts() {
        return gifts;
    }

    public void setGifts(List<Long> gifts) {
        this.gifts = gifts;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", friends=" + friends +
                ", gifts=" + gifts +
                '}';
    }
}

