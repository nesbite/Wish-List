package pl.edu.agh.io.wishlist.domain;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Gift {

    @Id
    private ObjectId id;

    private String name;
    private String description;

    public Gift() {
    }

    public Gift(String name) {
        this.name = name;
    }

    public Gift(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public ObjectId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return (description == null ? "" : description);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
