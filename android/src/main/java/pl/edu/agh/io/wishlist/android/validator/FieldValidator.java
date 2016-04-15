package pl.edu.agh.io.wishlist.android.validator;

import android.util.Patterns;

public class FieldValidator {

    private final static int LOGIN_MIN_LENGTH = 3;

    private static final int PASSWORD_MIN_LENGTH = 4;
    private static final int PASSWORD_MAX_LENGTH = 32;

    public Response validate(Field field, String request) {
        switch (field) {
            case LOGIN:
                return isLoginValid(request);
            case EMAIL:
                return isEmailValid(request);
            case PASSWORD:
                return isPasswordValid(request);
        }

        return null;
    }


    private Response isLoginValid(String login) {
        if (login == null) {
            return new Response("login can't be null");
        }

        if (login.length() > LOGIN_MIN_LENGTH) {
            return new Response("login's minimum length is " + LOGIN_MIN_LENGTH);
        }

        return new Response();
    }

    private Response isPasswordValid(String password) {
        if (password == null) {
            return new Response("password can't be null");
        }

        if (password.length() < PASSWORD_MIN_LENGTH) {
            return new Response("password's minimum length is " + PASSWORD_MIN_LENGTH);
        }

        if (password.length() > PASSWORD_MAX_LENGTH) {
            return new Response("password's maximum length is " + PASSWORD_MIN_LENGTH);
        }

        return new Response();
    }

    private Response isEmailValid(String email) {
        if (email == null) {
            return new Response("email can't be null");
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return new Response("email must be valid");
        }

        return new Response();
    }

    private class Response {

        private boolean valid;
        private String error;

        private Response() {
            this(true);
        }

        private Response(String error) {
            this(false, error);
        }

        private Response(boolean result) {
            this(result, null);
        }

        private Response(boolean result, String error) {
            this.valid = result;
            this.error = error;
        }

        public boolean isValid() {
            return valid;
        }

        public String getError() {
            return error;
        }

    }

}
