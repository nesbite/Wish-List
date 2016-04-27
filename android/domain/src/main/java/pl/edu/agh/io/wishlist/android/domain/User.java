package pl.edu.agh.io.wishlist.android.domain;

import java.util.List;

public class User {

    private String id;
    private String username;
    private List<String> friends;
    private List<String> gifts;

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getFriends() {
        return friends;
    }

    public List<String> getGifts() {
        return gifts;
    }
}
