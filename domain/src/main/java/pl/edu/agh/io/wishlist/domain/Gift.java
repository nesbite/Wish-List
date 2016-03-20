package pl.edu.agh.io.wishlist.domain;

public class Gift {
    private final long id;
    private String name;
    private String description;

    public Gift(long id, String name) {
        this.id = id;
        this.name = name;
        this.description = null;
    }

    public Gift(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void updateName(String name){
        this.name = name;
    }

    public void updateDescription(String description){
        this.description = description;
    }

    public void updateFields(String name, String description){
        this.name = name;
        this.description = description;
    }
}
