import javafx.event.ActionEvent;
import java.io.IOException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;

public class adminLoginController {

    @FXML
    Label userNameID;

    @FXML
    Label passwordID;

    @FXML
    TextField userNameField;

    @FXML
    PasswordField passwordField;

    @FXML
    Button loginButton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void loginButtonHandler(ActionEvent event) throws IOException {

        String uname = userNameField.getText();
        String pword = passwordField.getText();


        if (DatabaseHandler.validateLogin(uname, pword)) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminHome.fxml"));

            root = loader.load();

            adminHomeController adminHomeController = loader.getController();
            adminHomeController.displayName(uname);

            stage = (Stage) loginButton.getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();

         }
        else
         {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Wrong username or password");
                alert.showAndWait();
        }

        
    }
}