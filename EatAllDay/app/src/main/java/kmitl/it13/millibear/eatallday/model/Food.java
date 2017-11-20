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
    private String currency;
    private Long amount;
    private String unit;

    public Food(String id, String name, Long cost, String description, String userId, String image, String currency, Long amount, String unit) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.description = description;
        this.userId = userId;
        this.image = image;
        this.currency = currency;
        this.amount = amount;
        this.unit = unit;
    }

    public Food() {
    }

    protected Food(Parcel in) {
        id = in.readString();
        name = in.readString();
        if (in.readByte() == 0) {
            cost = null;
        } else {
            cost = in.readLong();
        }
        description = in.readString();
        userId = in.readString();
        image = in.readString();
        currency = in.readString();
        if (in.readByte() == 0) {
            amount = null;
        } else {
            amount = in.readLong();
        }
        unit = in.readString();
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
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
        dest.writeString(currency);
        if (amount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(amount);
        }
        dest.writeString(unit);
    }
}
