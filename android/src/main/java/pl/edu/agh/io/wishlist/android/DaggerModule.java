package pl.edu.agh.io.wishlist.android;

import dagger.Module;
import dagger.Provides;
import pl.edu.agh.io.wishlist.android.activity.LoginActivity;
import pl.edu.agh.io.wishlist.android.validator.Validator;

import javax.inject.Singleton;

@Module(injects = LoginActivity.class)
public class DaggerModule {

    @Provides
    @Singleton
    Validator provideValidator() {
        return new Validator();
    }

}
