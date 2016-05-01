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
import pl.edu.agh.io.wishlist.android.R;
import pl.edu.agh.io.wishlist.android.dagger.DaggerApplication;
import pl.edu.agh.io.wishlist.android.data.ServerCredentials;

import javax.inject.Inject;

@SuppressWarnings("WeakerAccess")
public class ProfileFragment extends Fragment {

    @Bind(R.id.user_avatar)
    ImageView userAvatar;

    @Bind(R.id.username)
    TextView username;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Dagger injection
        DaggerApplication.inject(this);

        // ButterKnife injection
        ButterKnife.bind(this, view);

        username.setText(sharedPreferences.getString("username", "username"));

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
}
