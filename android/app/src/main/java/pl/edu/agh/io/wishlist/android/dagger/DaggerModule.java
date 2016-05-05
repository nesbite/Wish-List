package pl.edu.agh.io.wishlist.android.dagger;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import dagger.Module;
import dagger.Provides;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import pl.edu.agh.io.wishlist.android.activity.LoginActivity;
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
                ProfileFragment.class
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
        try {
            ApplicationInfo ai = application.getPackageManager().getApplicationInfo(application.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;

            String host = bundle.getString("server_host");
            int port = bundle.getInt("server_port");

            return new ServerCredentials(host, port);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return new ServerCredentials();
        }
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
        return application.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
    }

}
