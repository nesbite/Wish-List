package pl.edu.agh.io.wishlist.android.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
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
import pl.edu.agh.io.wishlist.domain.User;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class UsersFragment extends Fragment {

    @Bind(R.id.users_list)
    ListView usersListView;

    @Inject
    ServerCredentials credentials;

    private List<User> users = new ArrayList<>();
    private ArrayAdapter<User> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        // Dagger injection
        DaggerApplication.inject(this);

        // Butterknife injection
        ButterKnife.bind(this, view);

        // load resources
        new LoadResourcesTask().execute();

        // adapter
        adapter = new ArrayAdapter<>(this.getActivity(), R.layout.drawer_list_item, R.id.title, users);
        usersListView.setAdapter(adapter);

        return view;
    }


    private class LoadResourcesTask extends AsyncTask<Void, Void, Collection<User>> {

        @Override
        protected Collection<User> doInBackground(Void... voids) {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return restTemplate.getForObject(credentials.getUrl(), Collection.class);
        }

        @Override
        protected void onPostExecute(Collection<User> users) {
            adapter.addAll(users);
            adapter.notifyDataSetInvalidated();
        }
    }
}
