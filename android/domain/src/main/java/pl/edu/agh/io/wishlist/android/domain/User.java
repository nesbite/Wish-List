package pl.edu.agh.io.wishlist.android.domain;

import java.util.List;

public class User {

    private String id;
    private String username;
    private String password;

    private List<String> friends;
    private List<Gift> gifts;

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

    public String getPassword() {
        return password;
    }
}
