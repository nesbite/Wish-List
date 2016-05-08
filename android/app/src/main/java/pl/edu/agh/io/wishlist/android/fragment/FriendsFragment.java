package pl.edu.agh.io.wishlist.android.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.edu.agh.io.wishlist.android.R;
import pl.edu.agh.io.wishlist.android.activity.DetailsActivity;
import pl.edu.agh.io.wishlist.android.auth.ServerCredentials;
import pl.edu.agh.io.wishlist.android.dagger.DaggerApplication;
import pl.edu.agh.io.wishlist.android.domain.User;
import pl.edu.agh.io.wishlist.android.fragment.adapter.FriendArrayAdapter;
import pl.edu.agh.io.wishlist.android.rest.LoadResourceTask;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
public class FriendsFragment extends Fragment {

    @Bind(R.id.friends_list)
    ListView usersListView;

    @Inject
    ServerCredentials credentials;

    @Inject
    RestTemplate restTemplate;

    @Inject
    FriendArrayAdapter adapter;

    @Inject
    SharedPreferences sharedPreferences;

    @Bind(R.id.user_stat1)
    TextView stat1;

    @Bind(R.id.user_stat2)
    TextView stat2;

    private Map<String, User> userMap = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        // Dagger injection
        DaggerApplication.inject(this);

        // ButterKnife injection
        ButterKnife.bind(this, view);

        // adapter
        usersListView.setAdapter(adapter);

        // FAB listener
        getActivity().findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Please enter your friend nickname");
                builder.setTitle("Add a friend");

                final EditText input = new EditText(getActivity());
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nick = input.getText().toString();

                        new AddFriendTask().execute(nick);

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        final String username = sharedPreferences.getString("username", "username");

        // load resources
        new LoadResourceTask<User[]>(restTemplate, credentials.getUrl("users"), User[].class) {
            @Override
            protected void onPostExecute(User[] users) {
                if (users != null) {
                    userMap.clear();
                    for (User user : users) {
                        userMap.put(user.getUsername(), user);
                    }

                    List<String> friendsNickList = userMap.get(username).getFriends();

                    List<User> friendsList = new ArrayList<>();
                    for (String username : friendsNickList) {
                        friendsList.add(userMap.get(username));
                    }

                    adapter.clear();
                    adapter.addAll(friendsList);
                    adapter.notifyDataSetChanged();

                    stat1.setText(String.valueOf(userMap.get(username).getFriends().size()));
                    stat2.setText(String.valueOf(userMap.size()));
                } else {
                    Toast.makeText(getActivity(), "Can't get users from server", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // ButterKnife unbind
        ButterKnife.unbind(this);
    }

    @OnItemClick(R.id.friends_list)
    public void onClick(int position) {
        User user = adapter.getItem(position);

        Bundle args = new Bundle();
        args.putString(ProfileFragment.USERNAME, user.getUsername());
        args.putBoolean(ProfileFragment.EDITABLE, false);

        Intent intent = new Intent(getActivity(), DetailsActivity.class);

        intent.putExtra(DetailsActivity.BUNDLE, args);
        intent.putExtra(DetailsActivity.FRAGMENT_TYPE, DetailsActivity.PROFILE);
        startActivity(intent);


    }


    class AddFriendTask extends AsyncTask<String, Void, HttpStatus> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Adding...");
            progressDialog.show();
        }

        @Override
        protected HttpStatus doInBackground(String... voids) {
            final String username = sharedPreferences.getString("username", "username");

            String nick = voids[0];

            if (username.equals(nick)) {
                return HttpStatus.CONFLICT;
            }

            if (!userMap.containsKey(nick)) {
                return HttpStatus.NOT_FOUND;
            }

            if (userMap.get(username).getFriends().contains(nick)) {
                return HttpStatus.CONFLICT;
            }

            try {
                ResponseEntity<String> entity = restTemplate.exchange(credentials.getUrl("friends/add/" + nick),
                        HttpMethod.PUT, null, String.class);

                if (entity.getStatusCode() == HttpStatus.OK) {
                    adapter.add(userMap.get(nick));
                    adapter.notifyDataSetChanged();
                }

                return entity.getStatusCode();
            } catch (Exception e) {
                e.printStackTrace();
                return HttpStatus.NOT_ACCEPTABLE;
            }
        }

        @Override
        protected void onPostExecute(HttpStatus httpStatus) {
            progressDialog.dismiss();

            String message = (httpStatus == HttpStatus.OK) ? "Friend added" : "Can't add a friend";

            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
    }
}
