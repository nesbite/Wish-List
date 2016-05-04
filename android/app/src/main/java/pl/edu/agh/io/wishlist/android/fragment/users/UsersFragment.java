package pl.edu.agh.io.wishlist.android.fragment.users;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import org.springframework.web.client.RestTemplate;
import pl.edu.agh.io.wishlist.android.R;
import pl.edu.agh.io.wishlist.android.dagger.DaggerApplication;
import pl.edu.agh.io.wishlist.android.rest.LoadResourceTask;
import pl.edu.agh.io.wishlist.android.rest.ServerCredentials;
import pl.edu.agh.io.wishlist.android.domain.User;

import javax.inject.Inject;

@SuppressWarnings("WeakerAccess")
public class UsersFragment extends Fragment {

    @Bind(R.id.users_list)
    ListView usersListView;

    @Inject
    ServerCredentials credentials;

    @Inject
    RestTemplate restTemplate;

    @Inject
    UserArrayAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        // Dagger injection
        DaggerApplication.inject(this);

        // ButterKnife injection
        ButterKnife.bind(this, view);

        // adapter
        usersListView.setAdapter(adapter);

        // load resources
        new LoadResourceTask<User[]>(restTemplate, credentials.getUrl("users"), User[].class) {
            @Override
            protected void onPostExecute(User[] users) {
                if (users != null) {
                    adapter.clear();
                    adapter.addAll(users);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "Can't get users from server", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // ButterKnife unbind
        ButterKnife.unbind(this);
    }


}
