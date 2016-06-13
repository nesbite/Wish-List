package pl.edu.agh.io.wishlist.domain.module;


public abstract class UserDetails {

    User user;

    String password;

    String email;

    public UserDetails() {

    }

    public User getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
