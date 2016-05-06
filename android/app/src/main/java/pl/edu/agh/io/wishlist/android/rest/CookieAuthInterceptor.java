package pl.edu.agh.io.wishlist.android.rest;

import android.content.SharedPreferences;
import android.util.Log;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import javax.inject.Inject;
import java.io.IOException;

public class CookieAuthInterceptor implements ClientHttpRequestInterceptor {

    private static final String TAG = CookieAuthInterceptor.class.getSimpleName();

    private SharedPreferences sharedPreferences;

    @Inject
    public CookieAuthInterceptor(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String cookie = sharedPreferences.getString("Cookie", null);

        Log.d(TAG, "intercept: " + request.getURI() + ", cookie: " + (cookie != null));

        if (cookie != null) {
            request.getHeaders().add("Cookie", cookie);
        }

        return execution.execute(request, body);
    }

}