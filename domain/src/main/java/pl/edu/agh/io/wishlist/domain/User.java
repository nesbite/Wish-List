package pl.edu.agh.io.wishlist.domain;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class User {
    @Id
    private long id;
    private String login;
    private String password;
    private List<Long> friends;

    public User(){}

    public User(long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.friends = new ArrayList<>();
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

    public void setId(long id){
        this.id = id;
    }
}

