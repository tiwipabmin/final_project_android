package kmitl.it13.millibear.eatallday.model;

public class History {

    private String hisId;
    private String userId;
    private String foodName;
    private String historyName;
    private String foodImage;
    private Long cost;
    private Long piece;
    private String time;
    private String type;

    public History() {
    }

    public History(String hisId, String userId, String foodName, String historyName, String foodImage, Long cost, Long piece, String time, String type) {
        this.hisId = hisId;
        this.userId = userId;
        this.foodName = foodName;
        this.historyName = historyName;
        this.foodImage = foodImage;
        this.cost = cost;
        this.piece = piece;
        this.time = time;
        this.type = type;
    }

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

    public Long getPiece() {
        return piece;
    }

    public void setPiece(Long piece) {
        this.piece = piece;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
