package pl.edu.agh.io.wishlist.android.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import butterknife.Bind;
import butterknife.ButterKnife;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import pl.edu.agh.io.wishlist.android.R;
import pl.edu.agh.io.wishlist.android.ServerCredentials;
import pl.edu.agh.io.wishlist.android.dagger.DaggerApplication;
import pl.edu.agh.io.wishlist.android.domain.User;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class UsersFragment extends Fragment {

    @Bind(R.id.users_list)
    ListView usersListView;

    @Inject
    ServerCredentials credentials;

    private ArrayAdapter<User> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        // Dagger injection
        DaggerApplication.inject(this);

        // Butterknife injection
        ButterKnife.bind(this, view);

        // adapter
        adapter = new UserArrayAdapter(getActivity());
        usersListView.setAdapter(adapter);

        // load resources
        new LoadResourcesTask().execute();

        return view;
    }


    private class LoadResourcesTask extends AsyncTask<Void, Void, User[]> {

        @Override
        protected User[] doInBackground(Void... voids) {
            Log.e("USERS", "background");
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Log.e("USERS", "url: " + credentials.getUrl());
            return restTemplate.getForObject(credentials.getUrl() + "/users", User[].class);
        }

        @Override
        protected void onPostExecute(User[] users) {
            adapter.clear();
            adapter.addAll(users);
            adapter.notifyDataSetChanged();
        }
    }
}
