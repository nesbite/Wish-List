package pl.edu.agh.io.wishlist.android.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.edu.agh.io.wishlist.android.R;
import pl.edu.agh.io.wishlist.android.activity.DetailsActivity;
import pl.edu.agh.io.wishlist.android.auth.ServerCredentials;
import pl.edu.agh.io.wishlist.android.dagger.DaggerApplication;
import pl.edu.agh.io.wishlist.android.domain.Gift;

import javax.inject.Inject;

@SuppressWarnings("WeakerAccess")
public class GiftDetailFragment extends Fragment {

    public static final String GIFT_EXTRA = "gift";
    public static final String EDITABLE = "editable";
    @Bind(R.id.giftImage)
    ImageView giftImage;

    @Bind(R.id.description)
    TextView description;

    @Inject
    ServerCredentials serverCredentials;

    @Inject
    RestTemplate restTemplate;

    private Gift gift;
    private boolean editable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerApplication.inject(this);

        Bundle bundle = getArguments();

        if (bundle != null) {
            gift = (Gift) bundle.getSerializable(GIFT_EXTRA);
            editable = bundle.getBoolean(EDITABLE);
        }

        if (gift != null) {
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(gift.getName());
            }
        }

        if (editable) {
            getActivity().findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "Are you sure?", Snackbar.LENGTH_LONG)
                            .setAction("Delete", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new DeleteGiftAsyncTask().execute();
                                }
                            }).show();
                }
            });
        } else {
            getActivity().findViewById(R.id.fab).setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        getActivity().findViewById(R.id.fab).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.fab).setOnClickListener(null);

        ButterKnife.unbind(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gift_detail, container, false);

        // ButterKnife injection
        ButterKnife.bind(this, rootView);

        // Show the dummy content as text in a TextView.
        giftImage.setImageResource(R.drawable.icon_gift_default);
        if (gift != null) {
            description.setText(gift.getDescription());
        }

        return rootView;
    }

    private class DeleteGiftAsyncTask extends AsyncTask<Void, Void, HttpStatus> {

        private final String TAG = DeleteGiftAsyncTask.class.getSimpleName();
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getActivity());
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
                    getActivity().finish();
                    break;
                default:
                    Toast.makeText(getActivity(), "Request failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
