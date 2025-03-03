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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

// public class userUploadPageController {

//     @FXML
//     private Label userTextLabel;

//     @FXML
//     private Button userUploadButton;

//     @FXML
//     private TextField userUploadField;

//     @FXML
//     private Button userUploadReturnButton;

//     private int userId;

//     public void setUserId(int userId) {
//         this.userId = userId;
//     }

//     @FXML
//     public void userUploadReturnButtonHandler(ActionEvent event) throws IOException {
//         FXMLLoader loader = new FXMLLoader(getClass().getResource("tiktokHomePage.fxml"));
//         Parent root = loader.load();
//         Stage stage = (Stage) userUploadReturnButton.getScene().getWindow();
//         Scene scene = new Scene(root);
//         stage.setScene(scene);
//         stage.show();
//     }

//     @FXML
//     public void userUploadButtonHandler(ActionEvent event) {
//         String userUploadText = userUploadField.getText();

//         if (userUploadText.isEmpty()) {
//             Alert alert = new Alert(AlertType.ERROR);
//             alert.setContentText("Please fill in all fields");
//             alert.showAndWait();
//             return;  
//         } 
//             String username = DatabaseHandler.getInstance().getUsernameById(userId);

//             tiktokUsersPost newPost = new tiktokUsersPost(0, username, userUploadText, getCurrentDate());

//         if (DatabaseHandler.getInstance().addTikTokUserPost(newPost)) {
//             Alert alert = new Alert(AlertType.INFORMATION);
//             alert.setContentText("Post uploaded successfully!");
//             alert.showAndWait();
//         } else {
//             Alert alert = new Alert(AlertType.ERROR);
//             alert.setContentText("Failed to upload post.");
//             alert.showAndWait();
//         }     

//     }

//     private String getCurrentDate() {
//         return java.time.LocalDateTime.now().toString();
//     }
// }

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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class userUploadPageController {

    @FXML
    private Label userTextLabel;

    @FXML
    private Button userUploadButton;

    @FXML
    private TextField userUploadField;

    @FXML
    private Button userUploadReturnButton;

    private int userId;
    private String currentUserName;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    @FXML
    public void userUploadReturnButtonHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("tiktokHomePage.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) userUploadReturnButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void userUploadButtonHandler(ActionEvent event) {
        String userUploadText = userUploadField.getText();

        if (userUploadText.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
            return;  
        }

        tiktokUsersPost newPost = new tiktokUsersPost(currentUserName, userUploadText);

        if (DatabaseHandler.getInstance().addTikTokUserPost(newPost)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Post uploaded successfully!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Failed to upload post.");
            alert.showAndWait();
        }
    }

    private String getCurrentDate() {
        return java.time.LocalDateTime.now().toString();
    }
}