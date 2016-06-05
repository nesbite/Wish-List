package pl.edu.agh.io.wishlist.android.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import pl.edu.agh.io.wishlist.android.R;
import pl.edu.agh.io.wishlist.android.auth.ServerCredentials;
import pl.edu.agh.io.wishlist.android.dagger.DaggerApplication;
import pl.edu.agh.io.wishlist.android.domain.User;
import pl.edu.agh.io.wishlist.android.fragment.adapter.FriendArrayAdapter;
import pl.edu.agh.io.wishlist.android.rest.LoadResourceTask;

@SuppressWarnings("WeakerAccess")
public class RequestsFragment extends Fragment {

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
        View view = inflater.inflate(R.layout.fragment_requests, container, false);

        // Dagger injection
        DaggerApplication.inject(this);

        // ButterKnife injection
        ButterKnife.bind(this, view);

        // adapter
        usersListView.setEmptyView(view.findViewById(R.id.empty_list_view));
        usersListView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().findViewById(R.id.fab).setVisibility(View.INVISIBLE);

        final String username = sharedPreferences.getString("username", "username");

        // load resources
        new LoadResourceTask<User>(restTemplate, credentials.getUrl("users/" + username), User.class) {
            @Override
            protected void onPostExecute(User user) {
                if (user != null) {
                    adapter.clear();
                    adapter.addAll(user.getFriendsRequests());
                    adapter.notifyDataSetChanged();

                    stat1.setText(String.valueOf(user.getFriendsRequests().size()));
                } else {
                    Toast.makeText(getActivity(), "Can't get users from server", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();

    }

    @Override
    public void onPause() {
        super.onPause();

        getActivity().findViewById(R.id.fab).setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // ButterKnife unbind
        ButterKnife.unbind(this);
    }

    @OnItemClick(R.id.friends_list)
    public void onClick(int position) {
        final String user = adapter.getItem(position);

        new AlertDialog.Builder(getActivity())
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new RequestTask(true, user).execute();
                    }
                })
                .setNegativeButton("Reject", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new RequestTask(false, user).execute();
                    }
                })
                .show();
    }

    private class RequestTask extends AsyncTask<Void, Void, HttpStatus> {
        private final boolean accept;
        private final String username;

        public RequestTask(boolean accept, String username) {
            this.accept = accept;
            this.username = username;
        }

        @Override
        protected HttpStatus doInBackground(Void... params) {
            try {
                String url = (accept ? "friends/add/" : "friends/requests/reject/") + username;

                ResponseEntity<String> entity = restTemplate.exchange(credentials.getUrl(url),
                        HttpMethod.PUT, null, String.class);

                return entity.getStatusCode();
            } catch (Exception e) {
                e.printStackTrace();
                return HttpStatus.NOT_ACCEPTABLE;
            }
        }

        @Override
        protected void onPostExecute(HttpStatus httpStatus) {
            super.onPostExecute(httpStatus);

            if(httpStatus.equals(HttpStatus.OK)) {
                adapter.remove(username);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
