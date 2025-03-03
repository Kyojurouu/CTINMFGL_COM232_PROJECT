import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class tiktokStartPageController {

    @FXML
    private Label passwordLabel;

    @FXML
    private Button userLoginButton;

    @FXML
    private PasswordField userPasswordField;

    @FXML
    private Button userSignUpButton;

    @FXML
    private TextField userUsernameField;

    @FXML
    private Label welcomeUserLabel;

    @FXML
    private Button usertoAdminButton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void usertoAdminButtonHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adminLogin.fxml"));
        root = loader.load();
        stage = (Stage) usertoAdminButton.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    @FXML
    public void userSignUpButtonHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("userSignUpPage.fxml"));
        root = loader.load();
        stage = (Stage) usertoAdminButton.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    @FXML
    public void userLoginButton(ActionEvent event) throws IOException {
        String userName = userUsernameField.getText();
        String password = userPasswordField.getText();

        if (DatabaseHandler.uservalidateLogin(userName, password)) {
            SessionManager.getInstance().setCurrentUserName(userName);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("tiktokHomePage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) userLoginButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid username or password");
            alert.showAndWait();
        }
    }

}
