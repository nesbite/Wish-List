package pl.edu.agh.io.wishlist.android.ui.drawer;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import butterknife.Bind;
import butterknife.ButterKnife;
import pl.edu.agh.io.wishlist.android.R;
import pl.edu.agh.io.wishlist.android.dagger.DaggerApplication;

import javax.inject.Inject;

@SuppressWarnings("WeakerAccess")
public class Drawer {

    private final Activity activity;
    private final ActionBar actionBar;

    private CharSequence activityTitle;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Bind(R.id.drawer_list)
    ListView drawerList;

    @Bind(R.id.left_drawer)
    LinearLayout drawerView;

    private ActionBarDrawerToggle drawerToggle;

    @Inject
    Menu drawerMenu;

    public Drawer(Activity activity) {
        this.activity = activity;
        this.activityTitle = activity.getTitle();
        this.actionBar = activity.getActionBar();

        if (actionBar == null) {
            throw new IllegalStateException("Drawer supports only android themes with ActionBar");
        }

        // ButterKnife
        ButterKnife.bind(this, activity);

        // Dagger
        DaggerApplication.inject(this);

        init();
    }

    public ActionBarDrawerToggle getToggle() {
        return drawerToggle;
    }

    private void init() {
        // configure action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // initialize menu with XML resource
        activity.getMenuInflater().inflate(R.menu.drawer_menu, drawerMenu);

        // set a custom shadow that overlays the main content when the drawer opens
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        drawerList.setAdapter(new DrawerListAdapter(drawerMenu));

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        drawerToggle = new DrawerToggle(
                activity,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                R.drawable.icon_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        );

        drawerLayout.setDrawerListener(drawerToggle);
    }

    public void setOnItemClickListener(ListView.OnItemClickListener listener) {
        drawerList.setOnItemClickListener(listener);
    }

    public void update(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    public void onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerView);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
    }

    /* Toggle for a navigation drawer, triggers activityTitle changes */
    private class DrawerToggle extends ActionBarDrawerToggle {

        public DrawerToggle(Activity activity, DrawerLayout drawerLayout,
                            int drawerImageRes, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
            super(activity, drawerLayout, drawerImageRes, openDrawerContentDescRes, closeDrawerContentDescRes);
        }

        public void onDrawerClosed(View view) {
            actionBar.setTitle(activity.getTitle());
            activity.invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
        }

        public void onDrawerOpened(View drawerView) {
            actionBar.setTitle(activityTitle);
            activity.invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
        }
    }

    public void selectItem(int position) {
        // update selected item and activityTitle, then close the drawer
        setDrawerTitle(drawerMenu.getItem(position).getTitle());
        drawerList.setItemChecked(position, true);
        drawerLayout.closeDrawer(drawerView);
    }

    public CharSequence getDrawerTitle() {
        return actionBar.getTitle();
    }

    public void setDrawerTitle(CharSequence drawerTitle) {
        this.activity.setTitle(drawerTitle);
        this.actionBar.setTitle(drawerTitle);
    }

    public Menu getDrawerMenu() {
        return drawerMenu;
    }
}
