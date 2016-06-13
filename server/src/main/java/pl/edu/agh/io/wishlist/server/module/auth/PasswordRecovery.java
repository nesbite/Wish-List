package pl.edu.agh.io.wishlist.server.module.auth;

public interface PasswordRecovery {

    void recoverAccount(String id);

    int getAttemptsCount(String id);

    boolean isRecoverable(String id);

}
