package kmitl.it13.millibear.eatallday.model;

/**
 * Created by tiwip on 11/13/2017.
 */

public class Food {

    private long id;
    private String name;
    private long cost;
    private String description;
    private String userId;
    private String image;

    public Food(long id, String name, long cost, String description, String userId, String image) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.description = description;
        this.userId = userId;
        this.image = image;
    }

    public Food() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
