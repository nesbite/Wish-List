package pl.edu.agh.io.wishlist.android.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.edu.agh.io.wishlist.android.R;
import pl.edu.agh.io.wishlist.android.auth.ServerCredentials;
import pl.edu.agh.io.wishlist.android.dagger.DaggerApplication;

import javax.inject.Inject;

public class SignUpActivity extends Activity {

    @Bind(R.id.input_name)
    EditText nameText;

    @Bind(R.id.input_login)
    EditText emailText;

    @Bind(R.id.input_password)
    EditText passwordText;

    @Bind(R.id.btn_signup)
    Button signUpButton;

    @Bind(R.id.link_login)
    TextView loginLinkText;

    @Inject
    ServerCredentials credentials;

    @Inject
    RestTemplate restTemplate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        DaggerApplication.inject(this);
    }

    // already registered - onClick
    public void back(View view) {
        // Finish the registration screen and return to the Login activity
        finish();
    }

    // signUpButton - onClick
    public void signUp(View view) {
        if (!validate()) {
            onSignUpFailed();
            return;
        }

        signUpButton.setEnabled(false);

        String username = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        new SignUpTask(username, email, password).execute();
    }


    private void onSignUpSuccess(String username, String password) {
        Intent data = new Intent();
        data.putExtra("username", username);
        data.putExtra("password", password);
        signUpButton.setEnabled(true);
        setResult(RESULT_OK, data);
        finish();
    }

    private void onSignUpFailed() {
        Toast.makeText(getBaseContext(), "Can't create an account!", Toast.LENGTH_LONG).show();
        signUpButton.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nameText.setError("at least 3 characters");
            valid = false;
        } else {
            nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }

    class SignUpTask extends AsyncTask<Void, Void, HttpStatus> {

        private final String username;
        private final String email;
        private final String password;
        private ProgressDialog progressDialog;

        public SignUpTask(String username, String email, String password) {
            this.username = username;
            this.email = email;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(SignUpActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Registering...");
            progressDialog.show();
        }

        @Override
        protected HttpStatus doInBackground(Void... voids) {
            try {
                MultiValueMap<String, Object> request = new LinkedMultiValueMap<>();
                request.add("username", username);
                request.add("firstName", username);
                request.add("lastName", username);
                request.add("email", email);
                request.add("password", password);
                request.add("matchingPassword", password);

                ResponseEntity<Void> entity = restTemplate.postForEntity(credentials.getUrl("user/registration"), request, Void.class);
                return entity.getStatusCode();
            } catch (HttpClientErrorException e) {
                return e.getStatusCode();
            } catch (Exception e) {
                e.printStackTrace();
                return HttpStatus.NOT_ACCEPTABLE;
            }
        }

        @Override
        protected void onPostExecute(HttpStatus httpStatus) {
            progressDialog.dismiss();

            switch (httpStatus) {
                case OK:
                    onSignUpSuccess(username, password);
                    break;
                default:
                    onSignUpFailed();
            }
        }
    }
}