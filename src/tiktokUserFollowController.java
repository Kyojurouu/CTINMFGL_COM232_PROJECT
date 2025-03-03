import java.io.IOException;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class tiktokUserFollowController {

    @FXML
    private Label userFollowUnFollowUsernameLabel;

    @FXML
    private Button userFollowButton;

    @FXML
    private Button userFollowReturnButton;

    @FXML
    private Label userFollowUsernameLabel;

    @FXML
    private Button userUnfollowButton;

    @FXML
    private TextField userUsernameFollowField;

    @FXML
    private TableView<tiktokUserFollow> userFollowUserTable;

    @FXML
    private TableColumn<tiktokUserFollow, String> userNameIDFollowCol;

    @FXML
    private TableColumn<tiktokUserFollow, String> otherUserNameIDFollowCol;

    @FXML
    private TableColumn<tiktokUserFollow, Integer> FollowIDCol;

    @FXML
    private TableView<tiktokUserFollow> userFollowerTable;

    @FXML
    private TableColumn<tiktokUserFollow, String> followerUserNameIDCol;

    @FXML
    private TableColumn<tiktokUserFollow, String> followerFollowUserNameIDCol;

    @FXML
    private TableColumn<tiktokUserFollow, Integer> FollowerIDCol;

    private String currentUserName;

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
        userFollowUsernameLabel.setText("Logged in as: " + currentUserName);
        displayUserFollows();
        displayUserFollowers();
    }

    @FXML
    public void initialize() {
        initializeCol();
    }

    @FXML
    public void initializeCol() {
        userNameIDFollowCol.setCellValueFactory(new PropertyValueFactory<>("userNameID"));
        otherUserNameIDFollowCol.setCellValueFactory(new PropertyValueFactory<>("followUserNameID"));
        FollowIDCol.setCellValueFactory(new PropertyValueFactory<>("followID"));

        followerUserNameIDCol.setCellValueFactory(new PropertyValueFactory<>("userNameID"));
        followerFollowUserNameIDCol.setCellValueFactory(new PropertyValueFactory<>("followUserNameID"));
        FollowerIDCol.setCellValueFactory(new PropertyValueFactory<>("followID"));
    }

    private void displayUserFollows() {
        if (currentUserName != null) {
            ObservableList<tiktokUserFollow> follows = DatabaseHandler.getInstance().getTikTokUserFollowByUserName(currentUserName);
            userFollowUserTable.setItems(follows);
        }
    }

    private void displayUserFollowers() {
        if (currentUserName != null) {
            ObservableList<tiktokUserFollow> followers = DatabaseHandler.getInstance().getTikTokUserFollowersByUserName(currentUserName);
            userFollowerTable.setItems(followers);
        }
    }

    @FXML
    public void userFollowReturnButtonHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("tiktokHomePage.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) userFollowReturnButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void userFollowButtonHandler(ActionEvent event) {
        String followUserNameID = userUsernameFollowField.getText().trim();

        if (followUserNameID.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Username cannot be empty.");
            return;
        }

        if (followUserNameID.equals(currentUserName)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "You cannot follow yourself.");
            return;
        }

        if (!DatabaseHandler.getInstance().userExists(followUserNameID)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "User does not exist.");
            return;
        }

        if (DatabaseHandler.getInstance().followExists(currentUserName, followUserNameID)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "You are already following this user.");
            return;
        }

        tiktokUserFollow follow = new tiktokUserFollow(0, currentUserName, followUserNameID);
        if (DatabaseHandler.getInstance().addTikTokUserFollow(follow)) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "You are now following " + followUserNameID);
            displayUserFollows();
            displayUserFollowers();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to follow user.");
        }
    }

    @FXML
    public void userUnfollowButtonHandler(ActionEvent event) {
        String followUserNameID = userUsernameFollowField.getText().trim();

        if (followUserNameID.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Username cannot be empty.");
            return;
        }

        if (followUserNameID.equals(currentUserName)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "You cannot unfollow yourself.");
            return;
        }

        if (!DatabaseHandler.getInstance().userExists(followUserNameID)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "User does not exist.");
            return;
        }

        if (!DatabaseHandler.getInstance().followExists(currentUserName, followUserNameID)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "You are not following this user.");
            return;
        }

        tiktokUserFollow follow = new tiktokUserFollow(0, currentUserName, followUserNameID);
        if (DatabaseHandler.getInstance().deleteTikTokUserFollow(follow)) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "You have unfollowed " + followUserNameID);
            displayUserFollows();
            displayUserFollowers();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to unfollow user.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
