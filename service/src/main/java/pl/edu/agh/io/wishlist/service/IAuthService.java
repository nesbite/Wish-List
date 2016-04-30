package pl.edu.agh.io.wishlist.service;

public interface IAuthService {

    void login(String username, String password);

    void register(String username, String password);
}
