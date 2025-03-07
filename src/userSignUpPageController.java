import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Alert.INFORMATION;
import java.util.regex.Pattern;

import java.io.IOException;

public class userSignUpPageController {

    @FXML
    private TextField FnameField;

    @FXML
    private TextField LnameField;

    @FXML
    private Button SignUpButton;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button returntotiktokPageButton;

    @FXML
    private TextField userNameField;

    @FXML
    private TextField pronounField;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void returntotiktokPageButtonHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("tiktokStartPage.fxml"));
        root = loader.load();
        stage = (Stage) returntotiktokPageButton.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void SignUpButtonHandler(ActionEvent event) throws IOException {
        String userName = userNameField.getText();
        String Fname = FnameField.getText();
        String Lname = LnameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String pronoun = pronounField.getText();

        userName = userName.trim();
        Fname = Fname.trim();
        Lname = Lname.trim();
        email = email.trim();
        password = password.trim();
        pronoun = pronoun.trim();

        if (userName.isEmpty() || Fname.isEmpty() || Lname.isEmpty() || email.isEmpty() || password.isEmpty() || pronoun.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
            return;  
        }

        if (!userName.startsWith("@")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Username must start with '@'");
            alert.showAndWait();
            return;
        }

        if (!Fname.matches("[a-zA-Z ]+")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("First Name must contain only alphabetic characters");
            alert.showAndWait();
            return;
        }

        if (!Lname.matches("[a-zA-Z ]+")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Last Name must contain only alphabetic characters");
            alert.showAndWait();
            return;
        }

        if (!email.endsWith("@gmail.com")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Email must end with '@gmail.com'");
            alert.showAndWait();
            return;
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Invalid email format");
            alert.showAndWait();
            return;
        }

        if (DatabaseHandler.getInstance().emailExists(email)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Email already exists");
            alert.showAndWait();
            return;
        }

        // String lowerCasePronoun = pronoun.toLowerCase();
        // if (!lowerCasePronoun.equals("he/him") && !lowerCasePronoun.equals("she/her") && !lowerCasePronoun.equals("they/them")) {
        //     Alert alert = new Alert(AlertType.ERROR);
        //     alert.setContentText("Pronoun must be 'He/Him', 'She/Her', or 'They/Them'");
        //     alert.showAndWait();
        //     return;
        // }

        tiktokUsers tiktokUsers = new tiktokUsers(userName, Fname, Lname, email, password, pronoun, 0, 0);

        if (DatabaseHandler.getInstance().addTikTokUser(tiktokUsers)) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setContentText("Account Created");
                alert.showAndWait();
        } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Account cannot be created");
                alert.showAndWait();   
        }   

    }

}