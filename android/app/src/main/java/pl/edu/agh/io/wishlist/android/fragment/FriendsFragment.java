package pl.edu.agh.io.wishlist.android.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        // Dagger injection
        DaggerApplication.inject(this);

        // ButterKnife injection
        ButterKnife.bind(this, view);

        // adapter
        usersListView.setEmptyView(view.findViewById(R.id.empty_list_view));
        usersListView.setAdapter(adapter);

        // FAB listener
        getActivity().findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Please enter your friend nickname");
                builder.setTitle("Add a friend");

                final EditText input = new EditText(getActivity());
                input.setFilters(new InputFilter[] {new InputFilter.LengthFilter(32)});
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

        // load resources
        new LoadResourceTask<User[]>(restTemplate, credentials.getUrl("friends"), User[].class) {
            @Override
            protected void onPostExecute(User[] users) {
                if (users != null) {
                    List<String> friends = new ArrayList<String>();
                    for (User user : users) {
                        friends.add(user.getUsername());
                    }

                    adapter.clear();
                    adapter.addAll(friends);
                    adapter.notifyDataSetChanged();

                    stat1.setText(String.valueOf(users.length));
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
        String user = adapter.getItem(position);

        Bundle args = new Bundle();
        args.putString(ProfileFragment.USERNAME, user);
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

            try {
                ResponseEntity<String> entity = restTemplate.exchange(credentials.getUrl("friends/add/" + nick),
                        HttpMethod.PUT, null, String.class);

                return entity.getStatusCode();
            } catch (Exception e) {
                e.printStackTrace();
                return HttpStatus.NOT_ACCEPTABLE;
            }
        }

        @Override
        protected void onPostExecute(HttpStatus httpStatus) {
            progressDialog.dismiss();

            String message = (httpStatus == HttpStatus.OK) ? "Request sent" : "Can't send a request";

            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
    }
}
