package pl.edu.agh.io.wishlist.domain;

import org.springframework.data.annotation.Id;

public class User {

    @Id
    private long id;
    private String login;
    private String password;
    private List<Long> friends;

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.friends = new ArrayList<>();
    }

<<<<<<< HEAD
    public long getId() {
=======
    public User(Long id, String login, String password) {
        this.id = String.valueOf(id);
        this.login = login;
        this.password = password;
    }

    public String getId() {
>>>>>>> User with annotated id
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

