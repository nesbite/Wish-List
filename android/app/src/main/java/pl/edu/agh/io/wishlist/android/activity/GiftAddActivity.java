package pl.edu.agh.io.wishlist.android.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.edu.agh.io.wishlist.android.R;
import pl.edu.agh.io.wishlist.android.auth.ServerCredentials;
import pl.edu.agh.io.wishlist.android.dagger.DaggerApplication;
import pl.edu.agh.io.wishlist.android.domain.Gift;

import javax.inject.Inject;

public class GiftAddActivity extends AppCompatActivity {

    @Bind(R.id.input_name)
    TextView name;

    @Bind(R.id.input_description)
    TextView description;

    @Inject
    RestTemplate restTemplate;

    @Inject
    ServerCredentials serverCredentials;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_add);

        DaggerApplication.inject(this);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_add)
    public void add(View view) {
        String name = this.name.getText().toString();
        String desc = this.description.getText().toString();
        final Gift gift = new Gift(name, desc);

        new DeleteGiftAsyncTask(gift).execute();

    }

    private class DeleteGiftAsyncTask extends AsyncTask<Void, Void, HttpStatus> {

        private Gift gift;

        public DeleteGiftAsyncTask(Gift gift) {
            this.gift = gift;
        }

        @Override
        protected HttpStatus doInBackground(Void... voids) {
            try {
                ResponseEntity<String> entity = restTemplate.postForEntity(serverCredentials.getUrl("gifts/add"), gift, String.class);

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
