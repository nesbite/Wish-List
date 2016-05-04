package pl.edu.agh.io.wishlist.android.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.view.MenuItem;
import pl.edu.agh.io.wishlist.android.R;
import pl.edu.agh.io.wishlist.android.fragment.profile.ProfileFragment;
import pl.edu.agh.io.wishlist.android.fragment.users.UsersFragment;

import javax.inject.Inject;

public class FragmentHandler {

    private final FragmentManager fragmentManager;

    @Inject
    public FragmentHandler(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void updateContent(MenuItem menuItem) {
        // update the main content by replacing fragments
        Fragment fragment = FragmentFactory.create(menuItem);

        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

    private static class FragmentFactory {

        private static Fragment create(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.nav_users:
                    return new UsersFragment();
                case R.id.nav_profile:
                    return new ProfileFragment();
                default:
                    return new ErrorFragment();
            }
        }
    }
}
