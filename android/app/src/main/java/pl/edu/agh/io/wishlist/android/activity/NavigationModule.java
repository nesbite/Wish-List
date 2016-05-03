package pl.edu.agh.io.wishlist.android.activity;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import dagger.Module;
import dagger.Provides;
import pl.edu.agh.io.wishlist.android.dagger.DaggerModule;

@Module(
        injects = {
                NavigationActivity.class
        },
        addsTo = DaggerModule.class,
        library = true
)
public class NavigationModule {

    private final AppCompatActivity activity;

    public NavigationModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return activity;
    }

    @Provides
    FragmentManager provideFragmentManager() {
        return activity.getFragmentManager();
    }

}
