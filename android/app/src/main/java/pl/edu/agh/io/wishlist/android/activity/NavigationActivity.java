package pl.edu.agh.io.wishlist.android.activity;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import butterknife.ButterKnife;
import pl.edu.agh.io.wishlist.android.R;
import pl.edu.agh.io.wishlist.android.dagger.DaggerApplication;
import pl.edu.agh.io.wishlist.android.rest.ServerCredentials;
import pl.edu.agh.io.wishlist.android.ui.drawer.Drawer;

import javax.inject.Inject;

@SuppressWarnings("WeakerAccess")
public class NavigationActivity extends AppCompatActivity {

    @Inject
    ServerCredentials credentials;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    Drawer drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        // Dagger injection
        DaggerApplication.plus(new NavigationModule(this)).inject(this);

        // ButterKnife binding
        ButterKnife.bind(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (drawer.onOptionsItemSelected(item)) {
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
        drawer.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Pass any configuration change to the drawer toggles
        drawer.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        drawer.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("NAV", "id: " + 14);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.e("NAV", "id: " + 13);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("NAV", "id: " + 12);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("NAV", "id: " + 11);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("NAV", "id: " + 10);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        Log.e("NAV", "id: " + 9);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("NAV", "id: " + 8);
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Log.e("NAV", "id: " + 7);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        Log.e("NAV", "id: " + 6);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("NAV", "id: " + 5);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        Log.e("NAV", "id: " + 4);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.e("NAV", "id: " + 3);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e("NAV", "id: " + 2);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.e("NAV", "id: " + 1);
    }
}