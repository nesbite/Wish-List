package pl.edu.agh.io.wishlist.android.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.Bind;
import butterknife.ButterKnife;
import org.springframework.web.client.RestTemplate;
import pl.edu.agh.io.wishlist.android.activity.GiftDetailsActivity;
import pl.edu.agh.io.wishlist.android.R;
import pl.edu.agh.io.wishlist.android.activity.GiftAddActivity;
import pl.edu.agh.io.wishlist.android.dagger.DaggerApplication;
import pl.edu.agh.io.wishlist.android.domain.User;
import pl.edu.agh.io.wishlist.android.fragment.adapter.GiftArrayAdapter;
import pl.edu.agh.io.wishlist.android.rest.LoadResourceTask;
import pl.edu.agh.io.wishlist.android.auth.ServerCredentials;

import javax.inject.Inject;

@SuppressWarnings("WeakerAccess")
public class ProfileFragment extends Fragment {

    @Bind(R.id.user_avatar)
    ImageView userAvatar;

    @Bind(R.id.username)
    TextView usernameTextView;

    @Bind(R.id.profile_stat1)
    TextView stat1;

    @Bind(R.id.profile_stat2)
    TextView stat2;

    @Bind(R.id.profile_stat3)
    TextView stat3;

    @Inject
    ServerCredentials credentials;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    RestTemplate restTemplate;

    @Inject
    GiftArrayAdapter adapter;

    private String username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, null);

        // Dagger injection
        DaggerApplication.inject(this);

        // ButterKnife injection
        ButterKnife.bind(this, view);

        // FAB listener
        getActivity().findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GiftAddActivity.class);
                startActivity(intent);
            }
        });

        username = sharedPreferences.getString("username", "username");
        usernameTextView.setText(username);

        // adapter
        ListView giftListView = new ListView(getActivity());
        giftListView.addHeaderView(view);
        giftListView.setAdapter(adapter);

        // listener
        giftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Clicked position " + position + "!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), GiftDetailsActivity.class);
                intent.putExtra(GiftDetailsActivity.GIFT_EXTRA, adapter.getItem(position - 1));
                startActivity(intent);
            }
        });

        return giftListView;
    }

    @Override
    public void onResume() {
        super.onResume();

        // load resource
        new LoadResourceTask<User>(restTemplate, credentials.getUrl("users/" + username), User.class) {
            public ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setCancelable(false);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
            }

            @Override
            protected void onPostExecute(User user) {
                progressDialog.dismiss();
                if (user != null) {
                    updateViews(user);
                } else {
                    Toast.makeText(getActivity(), "Can't load user information from server", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        getActivity().findViewById(R.id.fab).setOnClickListener(null);

        // ButterKnife unbind
        ButterKnife.unbind(this);
    }

    private void updateViews(User user) {
        stat1.setText(String.valueOf(user.getFriends().size()));
        stat2.setText(String.valueOf(user.getGifts().size()));
        // TODO add something to 3rd stat
//                stat3.setText(user.getUsername().charAt(0));

        adapter.clear();
        adapter.addAll(user.getGifts());
        adapter.notifyDataSetChanged();
    }

}