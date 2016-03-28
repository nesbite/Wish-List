package pl.edu.agh.io.wishlist.domain;


import org.springframework.data.annotation.Id;

public class Gift {
    @Id
    private long id;
    private String name;
    private String description;

    public Gift(String name) {
        this.name = name;
    }

    public Gift(String name, String description) {
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
        return (description == null ? "" : description);
    }

    public void setId(long id) {
        this.id = id;
    }
}
