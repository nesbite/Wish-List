package pl.edu.agh.io.wishlist.android;

import android.app.Application;
import dagger.ObjectGraph;

public class DaggerApplication extends Application {

    private ObjectGraph graph;

    @Override
    public void onCreate() {
        super.onCreate();

        graph = ObjectGraph.create(new DaggerModule());
    }

    public void inject(Object object) {
        graph.inject(object);
    }

}
