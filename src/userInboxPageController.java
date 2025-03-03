import java.io.IOException;

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


public class userInboxPageController {

    @FXML
    private Label displaySearchEmailLabel;

    @FXML
    private Label displaySearchFnameLabel;

    @FXML
    private Label displaySearchLnameLabel;

    @FXML
    private Label displaySearchPronounLabel;

    @FXML
    private Label displaySearchUsernameLabel;

    @FXML
    private Label displayUserNameID;

    @FXML
    private Button userReturnHomeButton;

    @FXML
    private Button userSearchUserButton;

    @FXML
    private TextField userSearchUserField;

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

    @FXML
    public void userSearchUserButtonHandler(ActionEvent event) {
        String searchUserNameID = userSearchUserField.getText().trim();

        if (searchUserNameID.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Username cannot be empty.");
            return;
        }

        tiktokUsers userDetails = DatabaseHandler.getInstance().getUserDetails(searchUserNameID);
        if (userDetails == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "User not found.");
            return;
        }

        displaySearchUsernameLabel.setText(userDetails.getUserNameID());
        displaySearchFnameLabel.setText(userDetails.getFirstName());
        displaySearchLnameLabel.setText(userDetails.getLastName());
        displaySearchEmailLabel.setText(userDetails.getEmail());
        displaySearchPronounLabel.setText(userDetails.getPronoun());

        ObservableList<tiktokUserFollow> follows = DatabaseHandler.getInstance().getTikTokUserFollowByUserName(searchUserNameID);
        userFollowUserTable.setItems(follows);

        ObservableList<tiktokUserFollow> followers = DatabaseHandler.getInstance().getTikTokUserFollowersByUserName(searchUserNameID);
        userFollowerTable.setItems(followers);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void userReturnHomeButtonHandler(ActionEvent event) throws IOException{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("tiktokHomePage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) userReturnHomeButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
}