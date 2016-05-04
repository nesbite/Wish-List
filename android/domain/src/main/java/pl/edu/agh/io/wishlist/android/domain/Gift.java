package pl.edu.agh.io.wishlist.android.domain;

import java.io.Serializable;

public class Gift implements Serializable {

    private String id;
    private String name;
    private String description;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
