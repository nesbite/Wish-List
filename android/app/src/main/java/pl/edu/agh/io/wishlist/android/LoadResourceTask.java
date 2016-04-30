package pl.edu.agh.io.wishlist.android;

import android.os.AsyncTask;
import android.util.Log;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class LoadResourceTask<T> extends AsyncTask<Void, Void, T> {

    private static final String TAG = LoadResourceTask.class.getSimpleName();

    private final String url;
    private final Class<T> type;


    public LoadResourceTask(String url, Class<T> type) {
        this.url = url;
        this.type = type;
    }

    @Override
    protected T doInBackground(Void... voids) {
        Log.i(TAG, "Loading: " + url);
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return restTemplate.getForObject(url, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
