import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.regex.Pattern;

public class userUpdateProfileController {

    @FXML
    private Label userEmailLabel;

    @FXML
    private TextField userEmailUpdateField;

    @FXML
    private Label userFirstNameLabel;

    @FXML
    private TextField userFirstNameUpdateField;

    @FXML
    private Label userLastNameLabel;

    @FXML
    private TextField userLastNameUpdateField;

    @FXML
    private Label userPasswordLabel;

    @FXML
    private TextField userPasswordUpdateField;

    @FXML
    private Label userPronounLabel;

    @FXML
    private TextField userPronounUpdateField;

    @FXML
    private Button userReturnUpdateButton;

    @FXML
    private Button userUpdateProfileButton;

    private String currentUserName;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    @FXML
    public void initialize() {
        userFirstNameUpdateField.setText(SessionManager.getInstance().getFirstName());
        userLastNameUpdateField.setText(SessionManager.getInstance().getLastName());
        userEmailUpdateField.setText(SessionManager.getInstance().getEmail());
        userPasswordUpdateField.setText(SessionManager.getInstance().getUserPassword());
        userPronounUpdateField.setText(SessionManager.getInstance().getPronoun());
    }

    @FXML
    private void userUpdateProfileButtonHandler(ActionEvent event) {
        String firstName = userFirstNameUpdateField.getText();
        String lastName = userLastNameUpdateField.getText();
        String email = userEmailUpdateField.getText();
        String password = userPasswordUpdateField.getText();
        String pronoun = userPronounUpdateField.getText();
        String userNameID = SessionManager.getInstance().getCurrentUserName();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || pronoun.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
            return;
        }

        if (!firstName.matches("[a-zA-Z ]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("First Name must contain only alphabetic characters");
            alert.showAndWait();
            return;
        }


        if (!lastName.matches("[a-zA-Z ]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Last Name must contain only alphabetic characters");
            alert.showAndWait();
            return;
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Invalid email format");
            alert.showAndWait();
            return;
        }

        if (!email.endsWith("@gmail.com")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Email must end with '@gmail.com'");
            alert.showAndWait();
            return;
        }

        // String lowerCasePronoun = pronoun.toLowerCase();
        // if (!lowerCasePronoun.equals("he/him") && !lowerCasePronoun.equals("she/her") && !lowerCasePronoun.equals("they/them")) {
        //     Alert alert = new Alert(Alert.AlertType.ERROR);
        //     alert.setContentText("Pronoun must be 'He/Him', 'She/Her', or 'They/Them'");
        //     alert.showAndWait();
        //     return;
        // }

        tiktokUsers user = new tiktokUsers(userNameID, firstName, lastName, email, password, pronoun, 0, 0);

        if (DatabaseHandler.updateTikTokUser(user)) {
            SessionManager.getInstance().setFirstName(firstName);
            SessionManager.getInstance().setLastName(lastName);
            SessionManager.getInstance().setEmail(email);
            SessionManager.getInstance().setUserPassword(password);
            SessionManager.getInstance().setPronoun(pronoun);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Profile updated successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Failed to update profile");
            alert.showAndWait();
        }
    }

    @FXML
    private void userReturnUpdateButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("userViewProfile.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) userReturnUpdateButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Failed to load the profile view page");
            alert.showAndWait();
        }
    }
}