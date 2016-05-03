package pl.edu.agh.io.wishlist.android.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import pl.edu.agh.io.wishlist.android.R;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
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

        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String username = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        // TODO: Implement your own signUp logic here.

        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignUpSuccess or onSignUpFailed
                        // depending on success
                        onSignUpSuccess();
                        // onSignUpFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    private void onSignUpSuccess() {
        signUpButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    private void onSignUpFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

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
}