package pl.edu.agh.io.wishlist.android.auth.interceptor;

import android.content.SharedPreferences;
import android.util.Log;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import javax.inject.Inject;
import java.io.IOException;

public class BasicAuthInterceptor implements ClientHttpRequestInterceptor {

    private static final String TAG = BasicAuthInterceptor.class.getSimpleName();

    private SharedPreferences sharedPreferences;

    @Inject
    public BasicAuthInterceptor(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
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