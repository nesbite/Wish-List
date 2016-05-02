package pl.edu.agh.io.wishlist.android.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.view.Menu;
import android.widget.PopupMenu;
import dagger.Module;
import dagger.Provides;
import pl.edu.agh.io.wishlist.android.R;
import pl.edu.agh.io.wishlist.android.dagger.DaggerModule;
import pl.edu.agh.io.wishlist.android.ui.drawer.Drawer;
import pl.edu.agh.io.wishlist.android.ui.drawer.DrawerListAdapter;

@Module(
        injects = {
                NavigationActivity.class
        },
        addsTo = DaggerModule.class,
        library = true
)
public class NavigationModule {

    private final Activity activity;

    public NavigationModule(NavigationActivity activity) {
        this.activity = activity;
    }

    @Provides
    Activity provideActivity() {
        return activity;
    }

    @Provides
    Menu provideDrawerMenu() {
        // initialize menu with XML resource
        Menu menu = new PopupMenu(activity.getApplicationContext(), null).getMenu();
        activity.getMenuInflater().inflate(R.menu.drawer_menu, menu);
        return menu;
    }

    @Provides
    Drawer provideDrawer(Activity activity, Menu drawerMenu, DrawerListAdapter drawerListAdapter) {
        return new Drawer(activity, drawerMenu, drawerListAdapter);
    }

    @Provides
    DrawerListAdapter provideDrawerListAdapter(Menu drawerMenu) {
        return new DrawerListAdapter(activity.getLayoutInflater(), drawerMenu);
    }

    @Provides
    FragmentManager provideFragmentManager() {
        return activity.getFragmentManager();
    }

}
