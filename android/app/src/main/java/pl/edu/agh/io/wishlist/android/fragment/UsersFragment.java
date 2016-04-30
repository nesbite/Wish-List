package pl.edu.agh.io.wishlist.android.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import pl.edu.agh.io.wishlist.android.LoadResourceTask;
import pl.edu.agh.io.wishlist.android.R;
import pl.edu.agh.io.wishlist.android.ServerCredentials;
import pl.edu.agh.io.wishlist.android.dagger.DaggerApplication;
import pl.edu.agh.io.wishlist.android.domain.User;

import javax.inject.Inject;

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

        // ButterKnife injection
        ButterKnife.bind(this, view);

        // adapter
        adapter = new UserArrayAdapter(getActivity());
        usersListView.setAdapter(adapter);

        // load resources
        new LoadResourceTask<User[]>(credentials, "users", User[].class) {
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


}
