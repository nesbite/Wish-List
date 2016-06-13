package pl.edu.agh.io.wishlist.domain.module;

import pl.edu.agh.io.wishlist.domain.Gift;

import java.util.List;

public abstract class User {

    String id;

    String username;

    List<String> friends;

    List<Gift> gifts;

    public User() {

    }

    public User(String id, String username) {
        this.id = id;
        this.username = username;
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

    public List<Gift> getGifts() {
        return gifts;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public void setGifts(List<Gift> gifts) {
        this.gifts = gifts;
    }
}
