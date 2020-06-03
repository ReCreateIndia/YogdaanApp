package recreate.india.yogdaan;

import com.google.firebase.firestore.ServerTimestamp;

public class PostModal {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PostModal(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public PostModal() {

    }
}
