package pl.edu.agh.io.wishlist.domain;


import org.springframework.data.annotation.Id;

public class Gift {
    @Id
    private long id;
    private String name;
    private String description;
    private long userID;

    public Gift(){}

    public Gift(long userID, String name) {
        this.userID = userID;
        this.name = name;
        this.description = null;
    }

    public Gift(long userID, String name, String description) {
        this.userID = userID;
        this.name = name;
        this.description = description;
    }

    public Gift(long giftID, long userID, String name, String description) {
        this.id = giftID;
        this.userID = userID;
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

    public long getUserID() {
        return userID;
    }

    public void setId(long id){
        this.id = id;
    }
}
