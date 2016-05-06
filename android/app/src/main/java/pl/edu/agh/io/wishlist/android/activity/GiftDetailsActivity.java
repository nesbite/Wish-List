package pl.edu.agh.io.wishlist.android.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.OnClick;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.edu.agh.io.wishlist.android.fragment.GiftDetailFragment;
import pl.edu.agh.io.wishlist.android.R;
import pl.edu.agh.io.wishlist.android.dagger.DaggerApplication;
import pl.edu.agh.io.wishlist.android.domain.Gift;
import pl.edu.agh.io.wishlist.android.auth.ServerCredentials;

import javax.inject.Inject;

public class GiftDetailsActivity extends AppCompatActivity {

    public static final String GIFT_EXTRA = "gift";

    private Gift gift;

    @Inject
    RestTemplate restTemplate;

    @Inject
    ServerCredentials serverCredentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        DaggerApplication.inject(this);

        gift = (Gift) getIntent().getSerializableExtra(GIFT_EXTRA);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putSerializable(GIFT_EXTRA, getIntent().getSerializableExtra(GIFT_EXTRA));

            GiftDetailFragment fragment = new GiftDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }
    }

    @OnClick(R.id.fab)
    public void onClick(View view) {
        Snackbar.make(view, "Are you sure?", Snackbar.LENGTH_LONG)
                .setAction("Delete", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DeleteGiftAsyncTask().execute();
                    }
                }).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown.
            navigateUpTo(new Intent(this, NavigationActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class DeleteGiftAsyncTask extends AsyncTask<Void, Void, HttpStatus> {

        private final String TAG = DeleteGiftAsyncTask.class.getSimpleName();
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(GiftDetailsActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Deleting...");
            progressDialog.show();
        }

        @Override
        protected HttpStatus doInBackground(Void... voids) {
            try {
                Log.i(TAG, "Logging in..");

                String url = serverCredentials.getUrl("gifts/remove/" + gift.getId());
                ResponseEntity<String> entity = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);

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
                    finish();
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "Request failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
