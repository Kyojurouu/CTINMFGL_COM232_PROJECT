import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javax.security.sasl.SaslServer;


public class tiktokUsersPost {
    private int userPostID;
    private String userNameID;
    private String userPost;
    private String userPostDate;

    public tiktokUsersPost(int userPostID, String userNameID, String userPost, String userPostDate) {
        this.userPostID = userPostID;
        this.userNameID = userNameID;
        this.userPost = userPost;
        this.userPostDate = userPostDate;
    }

    public tiktokUsersPost(String userNameID, String userPost) {
        this.userNameID = userNameID;
        this.userPost = userPost;
    }

    public tiktokUsersPost(String userPost) {
        this.userPost = userPost;
    }

    public int getUserPostID() {
        return userPostID;
    }

    public String getUserNameID() {
        return userNameID;
    }

    public String getUserPost() {
        return userPost;
    }

    public String getUserPostDate() {
        return userPostDate;
    }

    public void setUserPost(String userPost) {
        this.userPost = userPost;
    }
}
