package pl.edu.agh.io.wishlist.android.validator;

import android.util.Patterns;

public class Validator {

    private final static int LOGIN_MIN_LENGTH = 3;

    private static final int PASSWORD_MIN_LENGTH = 4;
    private static final int PASSWORD_MAX_LENGTH = 32;

    public boolean isLoginValid(String login) {
        return login != null && login.length() > LOGIN_MIN_LENGTH;
    }

    public boolean isPasswordValid(String password) {
        return password != null &&
                password.length() > PASSWORD_MIN_LENGTH &&
                password.length() < PASSWORD_MAX_LENGTH;
    }

    public boolean isEmailValid(String email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
