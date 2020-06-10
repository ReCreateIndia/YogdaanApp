package recreate.india.yogdaan;

public class OurWorkModel {
    String username;
    String date;
    String userImageUrl;
    String postImageUrl;
    String desc;


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

    public OurWorkModel(String username, String date, String userImageUrl, String postImageUrl, String desc) {
        this.username = username;
        this.date = date;
        this.userImageUrl = userImageUrl;
        this.postImageUrl = postImageUrl;
        this.desc = desc;
    }
    public  OurWorkModel(){

    }
}
