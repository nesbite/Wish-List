package pl.edu.agh.io.wishlist.android.domain;


import java.io.Serializable;

public class Gift implements Serializable {

    private String id;

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

    public String getId() {
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

    @Override
    public String toString() {
        return "Gift{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description=" + description +
                '}';
    }
}
