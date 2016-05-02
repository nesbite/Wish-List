package pl.edu.agh.io.wishlist.domain;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class User {

    @Id
    private String id;
    private String username;

    private List<String> friends;
    private List<String> gifts;

    public User() {
    }

    public User(String username) {
        this.username = username;
        this.friends = new ArrayList<>();
        this.gifts = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public List<String> getGifts() {
        return gifts;
    }

    public void setGifts(List<String> gifts) {
        this.gifts = gifts;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + username + '\'' +
                ", friends=" + friends +
                ", gifts=" + gifts +
                '}';
    }
}

