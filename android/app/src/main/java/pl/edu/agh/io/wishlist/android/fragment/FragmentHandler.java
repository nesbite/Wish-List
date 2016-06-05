package pl.edu.agh.io.wishlist.android.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import pl.edu.agh.io.wishlist.android.R;

import javax.inject.Inject;

public class FragmentHandler {

    private final FragmentManager fragmentManager;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    public FragmentHandler(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    private FragmentFactory fragmentFactory = new FragmentFactory();

    public void updateContent(MenuItem menuItem) {
        // update the main content by replacing fragments
        Fragment fragment = fragmentFactory.create(menuItem);

        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

    private class FragmentFactory {

        private Fragment create(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.nav_users:


                    return new FriendsFragment();
                case R.id.nav_profile:
                    Bundle args = new Bundle();
                    args.putString("username", sharedPreferences.getString("username", "username"));
                    args.putBoolean("editable", true);

                    Fragment fragment = new ProfileFragment();
                    fragment.setArguments(args);

                    return fragment;
                case R.id.nav_requests:
                    Bundle args2 = new Bundle();
                    args2.putString("username", sharedPreferences.getString("username", "username"));
                    args2.putBoolean("editable", true);

                    Fragment fragment2 = new RequestsFragment();
                    fragment2.setArguments(args2);
                    return fragment2;
                default:
                    return new ErrorFragment();
            }
        }
    }
}
