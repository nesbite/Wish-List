package pl.edu.agh.io.wishlist.android.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Password;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import pl.edu.agh.io.wishlist.android.R;
import pl.edu.agh.io.wishlist.android.ServerCredentials;
import pl.edu.agh.io.wishlist.android.dagger.DaggerApplication;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class LoginActivity extends Activity implements Validator.ValidationListener {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private static final int REQUEST_SIGN_UP = 0;

    @Length(min = 3, max = 32)
    @Bind(R.id.input_login)
    EditText loginText;

    @Password
    @Bind(R.id.input_password)
    EditText passwordText;

    @Bind(R.id.btn_login)
    Button loginButton;

    @Inject
    ServerCredentials credentials;

    private Validator validator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // ButterKnife
        ButterKnife.bind(this);

        // Dagger injection
        DaggerApplication.inject(this);

        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @OnClick(R.id.link_signup)
    public void signUp() {
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivityForResult(intent, REQUEST_SIGN_UP);
    }

    @OnClick(R.id.btn_login)
    public void login() {
        validator.validate();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGN_UP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signUp logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the HomeActivity
        moveTaskToBack(true);
    }

    @Override
    public void onValidationSucceeded() {
        loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String login = loginText.getText().toString();
        String password = passwordText.getText().toString();

        new LoginAsyncTask(login, password).execute();
/*        // TODO: Implement your own authentication logic here.
        // AsyncTask


        new Handler().postDelayed(new Runnable() {
            public void run() {
                loginButton.setEnabled(true);

                Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                startActivity(intent);

                finish();

                progressDialog.dismiss();
            }
        }, 1000);*/
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        }

        loginButton.setEnabled(true);
    }

    class LoginAsyncTask extends AsyncTask<Void, Void, HttpStatus> {

        private String username;
        private String password;

        public LoginAsyncTask(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected HttpStatus doInBackground(Void... voids) {
            Log.i(TAG, "Logging in..");
            try {
                // Populate the HTTP Basic Authentication header with the username and password
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.setAuthorization(authHeader);
                requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

                // Create a new RestTemplate instance
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                ResponseEntity<Void> response = restTemplate.exchange(credentials.getUrl("auth"), HttpMethod.POST, new HttpEntity<>(requestHeaders), Void.class);
                return response.getStatusCode();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(HttpStatus httpStatus) {
            switch (httpStatus) {
                case OK:

                    break;
                case UNAUTHORIZED:

                    break;
                default:

            }
        }
    }
}
