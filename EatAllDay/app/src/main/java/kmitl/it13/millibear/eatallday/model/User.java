package kmitl.it13.millibear.eatallday.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String userId;
    private String name;
    private String email;
    private String description = "Eat all day.";
    private String image = "https://upload.wikimedia.org/wikipedia/en/thumb/c/ce/User-info.svg/1024px-User-info.svg.png";
    private String money = "0";
    private String facebook;

    public User(String userId, String name, String email, String facebook, String image) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.facebook = facebook;
        this.image = image;
    }

    public User(String userId, String name, String email, String facebook) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.facebook = facebook;
    }

    public User(){}

    protected User(Parcel in) {
        userId = in.readString();
        name = in.readString();
        email = in.readString();
        description = in.readString();
        image = in.readString();
        money = in.readString();
        facebook = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(description);
        dest.writeString(image);
        dest.writeString(money);
        dest.writeString(facebook);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }
}