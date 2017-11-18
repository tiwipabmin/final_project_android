package kmitl.it13.millibear.eatallday.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Food implements Parcelable{

    private String id;
    private String name;
    private Long cost;
    private String description;
    private String userId;
    private String image;

    public Food(String id, String name, Long cost, String description, String userId, String image) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.description = description;
        this.userId = userId;
        this.image = image;
    }

    public Food() {
    }

    protected Food(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readString();
        }
        name = in.readString();
        if (in.readByte() == 0) {
            cost = null;
        } else {
            cost = in.readLong();
        }
        description = in.readString();
        userId = in.readString();
        image = in.readString();
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(id);
        }
        dest.writeString(name);
        if (cost == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(cost);
        }
        dest.writeString(description);
        dest.writeString(userId);
        dest.writeString(image);
    }
}
