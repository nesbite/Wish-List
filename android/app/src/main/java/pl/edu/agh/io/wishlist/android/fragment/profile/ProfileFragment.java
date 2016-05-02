package pl.edu.agh.io.wishlist.android.fragment.profile;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import org.springframework.web.client.RestTemplate;
import pl.edu.agh.io.wishlist.android.R;
import pl.edu.agh.io.wishlist.android.dagger.DaggerApplication;
import pl.edu.agh.io.wishlist.android.rest.LoadResourceTask;
import pl.edu.agh.io.wishlist.android.rest.ServerCredentials;
import pl.edu.agh.io.wishlist.android.domain.User;

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

    @Bind(R.id.giftList)
    ListView giftListView;

    @Inject
    ServerCredentials credentials;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    RestTemplate restTemplate;

    @Inject
    GiftArrayAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Dagger injection
        DaggerApplication.inject(this);

        // ButterKnife injection
        ButterKnife.bind(this, view);

        String username = sharedPreferences.getString("username", "username");
        usernameTextView.setText(username);

        // adapter
        giftListView.setAdapter(adapter);

        // load resource
        new LoadResourceTask<User>(restTemplate, credentials.getUrl("users/" + username), User.class) {
            @Override
            protected void onPostExecute(User user) {
                if (user != null) {
                    updateViews(user);
                } else {
                    Toast.makeText(getActivity(), "Can't load user information from server", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();

        return view;
    }

    @OnClick(R.id.add_gift)
    public void addGift() {
        Toast.makeText(getActivity(), "Gift add!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

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
