package pl.edu.agh.io.wishlist.android.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import pl.edu.agh.io.wishlist.android.R;

public class FragmentHandler {

    private final FragmentManager fragmentManager;
    private final Menu drawerMenu;

    public FragmentHandler(FragmentManager fragmentManager, Menu drawerMenu) {
        this.fragmentManager = fragmentManager;
        this.drawerMenu = drawerMenu;
    }

    public void updateContent(int position) {
        // update the main content by replacing fragments
        MenuItem menuItem = drawerMenu.getItem(position);
        Fragment fragment = FragmentFactory.create(menuItem);

        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

    public void update(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            updateContent(0);
        }
    }

    private static class FragmentFactory {

        private static Fragment create(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.nav_users:
                    return new UsersFragment();
                default:
                    return new ErrorFragment();
            }
        }
    }
}
