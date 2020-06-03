package recreate.india.yogdaan;

import java.sql.Timestamp;

public class BlogPost {

    String desc;
    String Url;
    String servertimestamp;

    public String getServertimestamp() {
        return servertimestamp;
    }

    public void setServertimestamp(String servertimestamp) {
        this.servertimestamp = servertimestamp;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        this.Url = url;
    }

    public BlogPost(String desc, String url, String servertimestamp) {
        this.desc = desc;
        this.Url = url;
        this.servertimestamp = servertimestamp;
    }

    public BlogPost(){

    }
}
