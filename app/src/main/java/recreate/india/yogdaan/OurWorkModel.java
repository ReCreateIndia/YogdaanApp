package recreate.india.yogdaan;

public class OurWorkModel {
   String body;
   String created;
   String image;
   String title;

    public OurWorkModel(String body, String created, String image, String title) {
        this.body = body;
        this.created = created;
        this.image = image;
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public  OurWorkModel(){

    }
}
