package pl.edu.agh.io.wishlist.android.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import butterknife.ButterKnife;
import pl.edu.agh.io.wishlist.android.R;
import pl.edu.agh.io.wishlist.android.dagger.DaggerApplication;
import pl.edu.agh.io.wishlist.android.data.ServerCredentials;
import pl.edu.agh.io.wishlist.android.fragment.FragmentHandler;
import pl.edu.agh.io.wishlist.android.ui.drawer.Drawer;

import javax.inject.Inject;

@SuppressWarnings("WeakerAccess")
public class NavigationActivity extends Activity {

    private Drawer drawer;

    private FragmentHandler fragmentHandler;

    @Inject
    ServerCredentials credentials;

    @Inject
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        // Dagger injection
        DaggerApplication.inject(this);

        // ButterKnife binding
        ButterKnife.bind(this);

        // initialize drawer
        drawer = new Drawer(this);
        drawer.setOnItemClickListener(new DrawerItemClickListener());
        drawer.update(savedInstanceState);

        // fragment handler
        fragmentHandler = new FragmentHandler(getFragmentManager(), drawer.getDrawerMenu());
        fragmentHandler.update(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (drawer.getToggle().onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawer.getToggle().syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Pass any configuration change to the drawer toggles
        drawer.getToggle().onConfigurationChanged(newConfig);
    }

    /* The click listener for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            drawer.selectItem(position);
            fragmentHandler.updateContent(position);
        }
    }

    @Override
    public void onBackPressed() {
        drawer.onBackPressed();
    }
}