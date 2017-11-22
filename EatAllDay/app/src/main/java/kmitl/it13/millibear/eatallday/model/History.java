package kmitl.it13.millibear.eatallday.model;

import android.os.Parcel;
import android.os.Parcelable;

public class History implements Parcelable {

    private String hisId;
    private String userId;
    private String foodName;
    private String historyName;
    private String foodImage;
    private Long cost;
    private String currency;
    private Long amount;
    private String unit;
    private String date;
    private String type;

    public History() {
    }

    public History(String hisId, String userId, String foodName, String historyName, String foodImage, Long cost, String currency, Long amount, String unit, String date, String type) {
        this.hisId = hisId;
        this.userId = userId;
        this.foodName = foodName;
        this.historyName = historyName;
        this.foodImage = foodImage;
        this.cost = cost;
        this.currency = currency;
        this.amount = amount;
        this.unit = unit;
        this.date = date;
        this.type = type;
    }

    protected History(Parcel in) {
        hisId = in.readString();
        userId = in.readString();
        foodName = in.readString();
        historyName = in.readString();
        foodImage = in.readString();
        if (in.readByte() == 0) {
            cost = null;
        } else {
            cost = in.readLong();
        }
        currency = in.readString();
        if (in.readByte() == 0) {
            amount = null;
        } else {
            amount = in.readLong();
        }
        unit = in.readString();
        date = in.readString();
        type = in.readString();
    }

    public static final Creator<History> CREATOR = new Creator<History>() {
        @Override
        public History createFromParcel(Parcel in) {
            return new History(in);
        }

        @Override
        public History[] newArray(int size) {
            return new History[size];
        }
    };

    public String getHisId() {
        return hisId;
    }

    public void setHisId(String hisId) {
        this.hisId = hisId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getHistoryName() {
        return historyName;
    }

    public void setHistoryName(String historyName) {
        this.historyName = historyName;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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
        dest.writeString(hisId);
        dest.writeString(userId);
        dest.writeString(foodName);
        dest.writeString(historyName);
        dest.writeString(foodImage);
        if (cost == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(cost);
        }
        dest.writeString(currency);
        if (amount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(amount);
        }
        dest.writeString(unit);
        dest.writeString(date);
        dest.writeString(type);
    }
}
