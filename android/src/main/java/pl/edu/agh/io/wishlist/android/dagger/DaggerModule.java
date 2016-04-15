package pl.edu.agh.io.wishlist.android.dagger;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.PopupMenu;
import dagger.Module;
import dagger.Provides;
import pl.edu.agh.io.wishlist.android.activity.LoginActivity;
import pl.edu.agh.io.wishlist.android.activity.NavigationActivity;
import pl.edu.agh.io.wishlist.android.fragment.FragmentHandler;
import pl.edu.agh.io.wishlist.android.ui.drawer.Drawer;
import pl.edu.agh.io.wishlist.android.ui.drawer.DrawerListAdapter;
import pl.edu.agh.io.wishlist.android.validator.FieldValidator;

import javax.inject.Singleton;

@Module(injects =
        {
                LoginActivity.class,
                NavigationActivity.class,
                Drawer.class,
                DrawerListAdapter.class,
                FragmentHandler.class
        },
        library = true)
class DaggerModule {

    private final DaggerApplication application;

    DaggerModule(DaggerApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    FieldValidator provideValidator() {
        return new FieldValidator();
    }

    @Provides
    @Singleton
    Context provideContext() {
        return application.getApplicationContext();
    }

    @Provides
    Menu provideMenu() {
        return new PopupMenu(application.getApplicationContext(), null).getMenu();
    }

    @Provides
    @Singleton
    LayoutInflater provideLayoutInflater() {
        return (LayoutInflater) application.getApplicationContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

}
