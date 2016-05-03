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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.edu.agh.io.wishlist.android.R;
import pl.edu.agh.io.wishlist.android.ServerCredentials;
import pl.edu.agh.io.wishlist.android.dagger.DaggerApplication;

import javax.inject.Inject;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class LoginActivity extends Activity implements Validator.ValidationListener {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private static final int REQUEST_SIGN_UP = 0;

    @Length(min = 3, max = 32)
    @Bind(R.id.input_login)
    EditText loginText;

    @Password(min = 4)
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
                Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                startActivity(intent);

                finish();
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
        String login = loginText.getText().toString();
        String password = passwordText.getText().toString();

        // authentication logic
        new LoginAsyncTask(login, password).execute();
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
    }

    class LoginAsyncTask extends AsyncTask<Void, Void, HttpStatus> {

        private ProgressDialog progressDialog;

        public LoginAsyncTask(String username, String password) {
            credentials.authenticate(username, password);
        }

        @Override
        protected void onPreExecute() {
            loginButton.setEnabled(false);
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Authenticating...");
            progressDialog.show();
        }

        @Override
        protected HttpStatus doInBackground(Void... voids) {
            Log.i(TAG, "Logging in..");
            try {
                // Create a new RestTemplate instance
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                ResponseEntity<Void> response = restTemplate.exchange(
                        credentials.getUrl("auth"),
                        HttpMethod.POST,
                        new HttpEntity<>(credentials.getHttpHeaders()),
                        Void.class
                );

                return response.getStatusCode();
            } catch (HttpClientErrorException e) {
                return e.getStatusCode();
            } catch (Exception e) {
                e.printStackTrace();
                return HttpStatus.NOT_ACCEPTABLE;
            }
        }

        @Override
        protected void onPostExecute(HttpStatus httpStatus) {
            loginButton.setEnabled(true);
            progressDialog.dismiss();

            switch (httpStatus) {
                case OK:
                    Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                    startActivity(intent);

                    finish();
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
