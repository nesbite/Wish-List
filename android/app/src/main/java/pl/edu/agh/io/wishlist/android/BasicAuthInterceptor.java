package pl.edu.agh.io.wishlist.android;

import android.content.SharedPreferences;
import android.util.Log;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import pl.edu.agh.io.wishlist.android.dagger.DaggerApplication;

import javax.inject.Inject;
import java.io.IOException;

public class BasicAuthInterceptor implements ClientHttpRequestInterceptor {

    private static final String TAG = BasicAuthInterceptor.class.getSimpleName();

    @Inject
    SharedPreferences sharedPreferences;

    public BasicAuthInterceptor() {
        // Dagger injection
        DaggerApplication.inject(this);
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String authHeader = sharedPreferences.getString("Authorization", null);

        Log.d(TAG, "intercepting, authHeader: " + authHeader);

        if (authHeader != null) {
            request.getHeaders().add("Authorization", authHeader);
        }

        return execution.execute(request, body);
    }

}