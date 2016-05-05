package pl.edu.agh.io.wishlist.android.dagger;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import dagger.Module;
import dagger.Provides;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import pl.edu.agh.io.wishlist.android.R;
import pl.edu.agh.io.wishlist.android.activity.LoginActivity;
import pl.edu.agh.io.wishlist.android.activity.SettingsActivity;
import pl.edu.agh.io.wishlist.android.fragment.profile.ProfileFragment;
import pl.edu.agh.io.wishlist.android.fragment.users.UsersFragment;
import pl.edu.agh.io.wishlist.android.rest.CookieAuthInterceptor;
import pl.edu.agh.io.wishlist.android.rest.ServerCredentials;

import javax.inject.Singleton;
import java.util.Collections;

@Module(injects =
        {
                LoginActivity.class,
                UsersFragment.class,
                ProfileFragment.class,
                SettingsActivity.class
        },
        library = true)
public class DaggerModule {

    private final DaggerApplication application;

    DaggerModule(DaggerApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    ServerCredentials provideServerCredentials() {
        String host = application.getString(R.string.server_host);
        int port = Integer.parseInt(application.getString(R.string.server_port));

        return new ServerCredentials(host, port);
    }

    @Provides
    @Singleton
    LayoutInflater provideLayoutInflater() {
        return (LayoutInflater) application.getApplicationContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    @Provides
    RestTemplate provideRestTemplate(CookieAuthInterceptor cookieAuthInterceptor) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.setInterceptors(Collections.<ClientHttpRequestInterceptor>singletonList(cookieAuthInterceptor));
        return restTemplate;
    }

    @Provides
    SharedPreferences provideSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

}
