package recreate.india.yogdaan;

public class FundRaiseModel {
    String username;
    String date;
    String userImageUrl;
    String postImageUrl;
    String desc;
    String currentDonation;
    String totalNeed;

    public String getCurrentDonation() {
        return currentDonation;
    }

    public void setCurrentDonation(String currentDonation) {
        this.currentDonation = currentDonation;
    }

    public String getTotalNeed() {
        return totalNeed;
    }

    public void setTotalNeed(String totalNeed) {
        this.totalNeed = totalNeed;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    String userId;

    public FundRaiseModel(String username, String date, String userImageUrl, String postImageUrl, String desc, String currentDonation, String totalNeed, String userId) {
        this.username = username;
        this.date = date;
        this.userImageUrl = userImageUrl;
        this.postImageUrl = postImageUrl;
        this.desc = desc;
        this.currentDonation = currentDonation;
        this.totalNeed = totalNeed;
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public String getPostImageUrl() {
        return postImageUrl;
    }

    public void setPostImageUrl(String postImageUrl) {
        this.postImageUrl = postImageUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public  FundRaiseModel(){

    }
}
