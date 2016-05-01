package pl.edu.agh.io.wishlist.android.data;

import android.os.AsyncTask;
import android.util.Log;
import org.springframework.web.client.RestTemplate;


public class LoadResourceTask<T> extends AsyncTask<Void, Void, T> {

    private static final String TAG = LoadResourceTask.class.getSimpleName();

    private final String url;
    private final Class<T> type;
    private final RestTemplate restTemplate;

    public LoadResourceTask(RestTemplate restTemplate, String url, Class<T> type) {
        this.restTemplate = restTemplate;
        this.url = url;
        this.type = type;
    }

    @Override
    protected T doInBackground(Void... voids) {
        Log.i(TAG, "Loading: " + url);
        try {
            return restTemplate.getForObject(url, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
