package recreate.india.yogdaan;

public class OurWorkModel {
   String body;
   String created;
   String imageUri;
   String title;

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

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public OurWorkModel(String body, String created, String imageUri, String title) {
        this.body = body;
        this.created = created;
        this.imageUri = imageUri;
        this.title = title;
    }

    public  OurWorkModel(){

    }
}
