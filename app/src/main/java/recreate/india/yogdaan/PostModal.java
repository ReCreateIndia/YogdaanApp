package recreate.india.yogdaan;

import com.google.firebase.firestore.ServerTimestamp;

public class PostModal {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public PostModal(String url, String desc) {
        this.url = url;
        this.desc = desc;
    }

    private String desc;


    public PostModal() {

    }
}
