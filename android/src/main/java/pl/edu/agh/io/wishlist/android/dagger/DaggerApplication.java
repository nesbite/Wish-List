package pl.edu.agh.io.wishlist.android.dagger;

import android.app.Application;
import dagger.ObjectGraph;

public class DaggerApplication extends Application {

    private static ObjectGraph graph;

    @Override
    public void onCreate() {
        super.onCreate();

        graph = ObjectGraph.create(new DaggerModule(this));
    }

    public static void inject(Object object) {
        graph.inject(object);
    }

}
