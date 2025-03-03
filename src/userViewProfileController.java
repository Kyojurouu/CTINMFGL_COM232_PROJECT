import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class userViewProfileController {

    @FXML
    private Label UsernameLabel;

    @FXML
    private Label displayEmailLabel;

    @FXML
    private Label displayFirstNameLabel;

    @FXML
    private Label displayFollowersLabel;

    @FXML
    private Label displayFollowingLabel;

    @FXML
    private Label displayLastNameLabel;

    @FXML
    private Label displayPronounLabel;

    @FXML
    private Label displayUserNameIDLabel;

    @FXML
    private Label displayUserPasswordLabel;

    @FXML
    private Button viewProfileReturnButton;

    @FXML
    private Button viewProfileUpdateButton;

    @FXML
    private Button viewProfileDeleteButton;

    @FXML
    public void initialize() {
        displayUserNameIDLabel.setText(SessionManager.getInstance().getCurrentUserName());
        displayFirstNameLabel.setText(SessionManager.getInstance().getFirstName());
        displayLastNameLabel.setText(SessionManager.getInstance().getLastName());
        displayEmailLabel.setText(SessionManager.getInstance().getEmail());
        displayUserPasswordLabel.setText(SessionManager.getInstance().getUserPassword());
        displayPronounLabel.setText(SessionManager.getInstance().getPronoun());
    }

    @FXML
    public void viewProfileReturnButtonHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("tiktokHomePage.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) viewProfileReturnButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void viewProfileUpdateButtonHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("userUpdateProfile.fxml"));
        Parent root = loader.load();
        userUpdateProfileController controller = loader.getController();
        controller.setCurrentUserName(SessionManager.getInstance().getCurrentUserName());
        Stage stage = (Stage) viewProfileUpdateButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void viewProfileDeleteButtonHandler(ActionEvent event) throws IOException {
        if (DatabaseHandler.profiledeleteTikTokUser(SessionManager.getInstance().getCurrentUserName())) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Account Deleted");
            alert.showAndWait();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("tiktokStartPage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) viewProfileDeleteButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } else {

            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Account cannot be deleted");
            alert.showAndWait();
            
        }
    }
}