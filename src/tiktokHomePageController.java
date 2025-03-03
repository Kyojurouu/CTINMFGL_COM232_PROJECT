import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.net.URI;
import javafx.scene.control.Hyperlink;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class tiktokHomePageController {
    
    @FXML
    private Label GreetingsUsernameLabel;

    @FXML
    private Button userUpdatePostButton;

    @FXML
    private TextField userUpdatePostField;

    @FXML
    private Label welcomeUserLabel;
  
    @FXML
    private Button usercreatePostButton;

    @FXML
    private Button userlogOutButton;

    @FXML
    private Button userviewProfileButton;

    @FXML
    private Button userSearchButton;

    @FXML
    private TableColumn<tiktokUsersPost, String> userViewPostCol;

    @FXML
    private TableView<tiktokUsersPost> userViewTable;

    @FXML
    private TableColumn<tiktokUsersPost, String> userViewUserNameIDCol;

    @FXML
    private Button userToFollowingPage;

    @FXML
    private Button gotoTiktokButton;

    ObservableList<tiktokUsersPost> tiktokUsersPost = FXCollections.observableArrayList();

    @FXML
    public void userlogOutButtonHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("tiktokStartPage.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) userlogOutButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void userviewProfileButtonHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("userViewProfile.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) userviewProfileButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void usercreatePostButtonHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("userUploadPage.fxml"));
        Parent root = loader.load();
        userUploadPageController controller = loader.getController();
        controller.setCurrentUserName(SessionManager.getInstance().getCurrentUserName());
        Stage stage = (Stage) usercreatePostButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void userSearchButtonHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("tiktokUserFollow.fxml"));
        Parent root = loader.load();
        tiktokUserFollowController controller = loader.getController();
        controller.setCurrentUserName(SessionManager.getInstance().getCurrentUserName());
        Stage stage = (Stage) userSearchButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void userToFollowingPageHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("userInboxPage.fxml"));
        Parent root = loader.load();
        userInboxPageController controller = loader.getController();
        Stage stage = (Stage) userToFollowingPage.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void gotoTiktokButtonHandler(ActionEvent event) throws URISyntaxException,IOException {
        Desktop.getDesktop().browse(new URI("www.tiktok.com/explore"));
    }

    @FXML
    public void initialize() {
        initializeCol();
        displaytiktokUsersPost();
        GreetingsUsernameLabel.setText(SessionManager.getInstance().getCurrentUserName());
    }

    public void initializeCol() {
        userViewUserNameIDCol.setCellValueFactory(new PropertyValueFactory<>("userNameID"));
        userViewPostCol.setCellValueFactory(new PropertyValueFactory<>("userPost"));
        userViewTable.setItems(getTikTokUsersPost());    
    }

    private ObservableList<tiktokUsersPost> getTikTokUsersPost() {
        return tiktokUsersPost;
    }
    
    public void displaytiktokUsersPost() {
        ResultSet result = DatabaseHandler.getTikTokUsersPost();
        tiktokUsersPost.clear();
    
        try {
            while (result.next()) {
                int userPostID = result.getInt("PostID");
                String userNameID = result.getString("UserNameID");
                String userPost = result.getString("Post");
                String userPostDate = result.getString("PostDate");
    
                tiktokUsersPost post = new tiktokUsersPost(userPostID, userNameID, userPost, userPostDate);
                tiktokUsersPost.add(post);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        userViewTable.setItems(tiktokUsersPost);
    }

    @FXML
    public void userUpdatePostButtonHandler(ActionEvent event) {
        tiktokUsersPost selectedtiktokUsersPost = userViewTable.getSelectionModel().getSelectedItem();
    
        if (selectedtiktokUsersPost == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No post selected");
            alert.showAndWait();
            return;
        }
    
        String userPost = userUpdatePostField.getText();
        userPost = userPost.trim();
    
        if (userPost.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Empty Post");
            alert.showAndWait();
            return;
        }
    
        int userPostID = selectedtiktokUsersPost.getUserPostID();
        String currentUserName = SessionManager.getInstance().getCurrentUserName();
    
        if (DatabaseHandler.getInstance().updateTikTokUsersPost(userPost, userPostID, currentUserName)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Post Updated");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Post cannot be updated");
            alert.showAndWait();
        }
    
        displaytiktokUsersPost();
    }
    
    @FXML
    public void userDeletePostButtonHandler(ActionEvent event) {
        tiktokUsersPost selectedtiktokUsersPost = userViewTable.getSelectionModel().getSelectedItem();
    
        if (selectedtiktokUsersPost == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No post selected");
            alert.showAndWait();
            return;
        }
    
        int userPostID = selectedtiktokUsersPost.getUserPostID();
        String currentUserName = SessionManager.getInstance().getCurrentUserName();
    
        if (DatabaseHandler.getInstance().deleteTikTokUsersPost(userPostID, currentUserName)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Post Deleted");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Post cannot be deleted");
            alert.showAndWait();
        }
    
        displaytiktokUsersPost();
    }
}