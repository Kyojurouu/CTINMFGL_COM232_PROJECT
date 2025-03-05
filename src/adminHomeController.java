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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.naming.spi.DirStateFactory;
import javax.xml.transform.Templates;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Observable;
import java.util.regex.Pattern;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;


public class adminHomeController {

    ObservableList<adminUsers> adminUsers = FXCollections.observableArrayList();

    ObservableList<tiktokUsers> tiktokUsers = FXCollections.observableArrayList();

    ObservableList<tiktokUsersPost> tiktokUsersPost = FXCollections.observableArrayList();

    @FXML
    Label adminWelcome;

    @FXML
    private TableColumn<adminUsers, String> adminIDCol;

    @FXML
    private TableColumn<adminUsers, String> adminAccCreatedCol;

    @FXML
    private TableColumn<adminUsers, String> adminPasswordCol;

    @FXML
    private TableView<adminUsers> adminTable;

    @FXML
    private TableView<tiktokUsers> usersTable;

    @FXML
    private TableColumn<adminUsers, String> adminUsernameCol;

    @FXML
    private Button adminCreateButton;

    @FXML
    private Button adminDeleteButton;
    
    @FXML
    private Button adminUpdateButton;  
    
    @FXML
    private Label addAdminPassword;

    @FXML
    private PasswordField addAdminPasswordField;

    @FXML
    private Label addAdminUser;

    @FXML
    private TextField addAdminUserField;

    @FXML
    private TableColumn<tiktokUsers, String> UserNameIDCol;

    @FXML
    private TableColumn<tiktokUsers, String> FirstNameCol;

    @FXML
    private TableColumn<tiktokUsers, String> LastNameCol;

    @FXML
    private TableColumn<tiktokUsers, String> EmailCol;

    @FXML
    private TableColumn<tiktokUsers, String> UserPasswordCol;

    @FXML
    private TableColumn<tiktokUsers, String> PronounCol;

    @FXML
    private TableColumn<tiktokUsers, String> UserFollowersCol;

    @FXML
    private TableColumn<tiktokUsers, String> UserFollowingCol;

    @FXML
    private TextField updateEmailField;

    @FXML
    private TextField updateFirstNameField;

    @FXML
    private TextField updateLastNameField;

    @FXML
    private TextField updatePronounField;

    @FXML
    private TextField updateUserNameIDField;

    @FXML
    private TextField updateUserPasswordField;

    @FXML
    private Button userDeleteButton;

    @FXML
    private Button userUpdateButton;

    @FXML
    private Button userDeletePostButton;

    @FXML
    private Button userPostUpdateButton;

    @FXML
    private TextField userUpdatePostField;

    @FXML
    private TableView<tiktokUsersPost> usersPostTable;

    @FXML
    private TableColumn<tiktokUsersPost, String> userNameIDPostCol;

    @FXML
    private TableColumn<tiktokUsersPost, String> userPostCol;

    @FXML
    private TableColumn<tiktokUsersPost, String> userPostDateCol;

    @FXML
    private TableColumn<tiktokUsersPost, Integer> userPostIDCol;

    @FXML
    private TableView<tiktokUserFollow> userFollowUserTable;

    @FXML
    private TableColumn<tiktokUserFollow, String> userNameIDFollowCol;

    @FXML
    private TableColumn<tiktokUserFollow, String> otherUserNameIDFollowCol;

    @FXML
    private TableColumn<tiktokUserFollow, Integer> FollowIDCol;

    @FXML
    private Button returntoAdminLoginButton;

    @FXML
    private Button userFollowDeleteButton;

    @FXML
    private TextField createUsernameField;

    @FXML
    private Button createNewUserButton;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");


    public void displayName(String uname) {
        adminWelcome.setText("Welcome, " + uname + "!");
        initializeCol();
        displayadminUser();
        displaytiktokUser();
        displaytiktokUsersPost();

    }

    @FXML
    private void initialize() {
    usersTable.setOnMouseClicked(event1 -> {
        tiktokUsers selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            createUsernameField.setText(selectedUser.getUserNameID());
            updateFirstNameField.setText(selectedUser.getFirstName());
            updateLastNameField.setText(selectedUser.getLastName());
            updateEmailField.setText(selectedUser.getEmail());
            updateUserPasswordField.setText(selectedUser.getUserPassword());
            updatePronounField.setText(selectedUser.getPronoun());
        }
    });
}

    public void initializeCol() {
        adminIDCol.setCellValueFactory(new PropertyValueFactory<>("adminID"));
        adminUsernameCol.setCellValueFactory(new PropertyValueFactory<>("adminUsername"));
        adminPasswordCol.setCellValueFactory(new PropertyValueFactory<>("adminPassword"));
        adminAccCreatedCol.setCellValueFactory(new PropertyValueFactory<>("adminAccCreated"));
        adminTable.setItems(getAdminUsers());

        UserNameIDCol.setCellValueFactory(new PropertyValueFactory<>("UserNameID"));
        FirstNameCol.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        LastNameCol.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        EmailCol.setCellValueFactory(new PropertyValueFactory<>("Email"));
        UserPasswordCol.setCellValueFactory(new PropertyValueFactory<>("UserPassword"));
        PronounCol.setCellValueFactory(new PropertyValueFactory<>("Pronoun"));
        UserFollowersCol.setCellValueFactory(new PropertyValueFactory<>("UserFollowers"));
        UserFollowingCol.setCellValueFactory(new PropertyValueFactory<>("UserFollowing"));
        usersTable.setItems(getTikTokUsers());

        userNameIDPostCol.setCellValueFactory(new PropertyValueFactory<>("userNameID"));
        userPostCol.setCellValueFactory(new PropertyValueFactory<>("userPost"));
        userPostDateCol.setCellValueFactory(new PropertyValueFactory<>("userPostDate"));
        userPostIDCol.setCellValueFactory(new PropertyValueFactory<>("userPostID"));
        usersPostTable.setItems(getTikTokUsersPost());

        userNameIDFollowCol.setCellValueFactory(new PropertyValueFactory<>("userNameID"));
        otherUserNameIDFollowCol.setCellValueFactory(new PropertyValueFactory<>("followUserNameID"));
        FollowIDCol.setCellValueFactory(new PropertyValueFactory<>("followID"));
        userFollowUserTable.setItems(getTikTokUserFollows());

    }

    private void displayadminUser() {

        ResultSet result = DatabaseHandler.getadminUsers();
        adminUsers.clear();

        try {
            while (result.next()) {
                String id = result.getString("id");
                String adminUname = result.getString("adminUser");
                String adminPword = result.getString("adminPassword");
                String accCreated = result.getString("account_created");

                adminUsers newadminUser = new adminUsers(Integer.parseInt(id), adminUname, adminPword, accCreated);
                adminUsers.add(newadminUser);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        adminTable.setItems(adminUsers);
    }

        private ObservableList<adminUsers> getAdminUsers() {
        return adminUsers;
        }


        @FXML
        private void returntoAdminLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("tiktokStartPage.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) returntoAdminLoginButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

        @FXML
        private void createadminUser(ActionEvent event) {

            String adminUser = addAdminUserField.getText();
            String adminPassword = addAdminPasswordField.getText();

            adminUser = adminUser.trim();
            adminPassword = adminPassword.trim();

            if (adminUser.length() == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Empty Username");
                alert.showAndWait();
            }

            if (adminPassword.length() == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Empty Password");
                alert.showAndWait();

            }

            adminUsers newadminUser = new adminUsers(0, adminUser, adminPassword, "");

            if (DatabaseHandler.addadminUser(newadminUser)) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setContentText("Account Created");
                alert.showAndWait();

            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Account cannot be created");
                alert.showAndWait();
            }

            displayadminUser();

        }

        @FXML
        private void deleteadminUser(ActionEvent event) {

        adminUsers newadminUser = adminTable.getSelectionModel().getSelectedItem();

        if (newadminUser == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No account selected");
            alert.showAndWait();
            return;
        }

        String adminUser = newadminUser.getAdminUsername();

        if (DatabaseHandler.deleteadminUser(newadminUser)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Account Deleted");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Account cannot be deleted");
            alert.showAndWait();
        }

        displayadminUser();
    }

    @FXML
    private void updateadminUser(ActionEvent event) {
        
    adminUsers selectedAdminUser = adminTable.getSelectionModel().getSelectedItem();

    if (selectedAdminUser == null) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("No account selected");
        alert.showAndWait();
        return;
    }

    String adminUser = addAdminUserField.getText();
    String adminPassword = addAdminPasswordField.getText();

    adminUser = adminUser.trim();
    adminPassword = adminPassword.trim();

    if (adminUser.length() == 0) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Empty Username");
        alert.showAndWait();
        return;
    }

    if (adminPassword.length() == 0) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Empty Password");
        alert.showAndWait();
        return;
    }

    selectedAdminUser.setAdminUsername(adminUser);
    selectedAdminUser.setAdminPassword(adminPassword);

    if (DatabaseHandler.updateadminUser(selectedAdminUser)) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Account Updated");
        alert.showAndWait();
    } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Account cannot be updated");
        alert.showAndWait();
    }

    displayadminUser();

    ////////////////////////////////////////////////////////// USERS SIDE //////////////////////////////////////////////////////////

}

public void displaytiktokUser() {
    ResultSet result = DatabaseHandler.getTikTokUsers();
    tiktokUsers.clear();

    try {
        while (result.next()) {
            String userName = result.getString("UserNameID");
            String Fname = result.getString("FirstName");
            String Lname = result.getString("LastName");
            String email = result.getString("Email");
            String password = result.getString("UserPassword");
            String pronoun = result.getString("Pronoun");
            int following = result.getInt("UserFollowing");
            int followers = result.getInt("UserFollowers");

            tiktokUsers selectedtiktokUsers = new tiktokUsers(userName, Fname, Lname, email, password, pronoun, following, followers);
            tiktokUsers.add(selectedtiktokUsers);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    usersTable.setItems(tiktokUsers);
    usersTable.refresh();
}

private void displayUserFollowTable() {
    userFollowUserTable.setItems(getTikTokUserFollows());
    userFollowUserTable.refresh();
}

    private ObservableList<tiktokUsers> getTikTokUsers() {
        return tiktokUsers;
    }

    @FXML
    private void addtiktokUser(ActionEvent event) {

        String adduserName = createUsernameField.getText();
        String addfirstName = updateFirstNameField.getText();
        String addlastName = updateLastNameField.getText();
        String addemail = updateEmailField.getText();
        String addpassword = updateUserPasswordField.getText();
        String addpronoun = updatePronounField.getText();

        adduserName = adduserName.trim();
        addfirstName = addfirstName.trim();
        addlastName = addlastName.trim();
        addemail = addemail.trim();
        addpassword = addpassword.trim();
        addpronoun = addpronoun.trim();

        if (adduserName.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Empty Username");
            alert.showAndWait();
            return;
        }

        if (!adduserName.startsWith("@")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Username must start with '@'");
            alert.showAndWait();
            return;
        }

        if (addfirstName.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Empty First Name");
            alert.showAndWait();
            return;
        }

        if (!addfirstName.matches("[a-zA-Z ]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("First Name must contain only alphabetic characters");
            alert.showAndWait();
            return;
        }
    
        if (addlastName.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Empty Last Name");
            alert.showAndWait();
            return;
        }

        if (!addlastName.matches("[a-zA-Z ]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Last Name must contain only alphabetic characters");
            alert.showAndWait();
            return;
        }
    
        if (addemail.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Empty Email");
            alert.showAndWait();
            return;
        }

        if (!addemail.endsWith("@gmail.com")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Email must end with '@gmail.com'");
            alert.showAndWait();
            return;
        }

        if (!EMAIL_PATTERN.matcher(addemail).matches()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Invalid email format");
            alert.showAndWait();
            return;
        }
    
        if (addpassword.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Empty Password");
            alert.showAndWait();
            return;
        }
    
        if (addpronoun.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Empty Pronoun");
            alert.showAndWait();
            return;
        }

        String addlowerCasePronoun = addpronoun.toLowerCase();
        if (!addlowerCasePronoun.equals("he/him") && !addlowerCasePronoun.equals("she/her") && !addlowerCasePronoun.equals("they/them")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Pronoun must be 'He/Him', 'She/Her', or 'They/Them'");
            alert.showAndWait();
            return;
        }

        tiktokUsers tiktokUsers = new tiktokUsers(adduserName, addfirstName, addlastName, addemail, addpassword, addpronoun, 0, 0);

        if (DatabaseHandler.getInstance().addTikTokUser(tiktokUsers)) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setContentText("Account Created");
                alert.showAndWait();
        } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Account cannot be created");
                alert.showAndWait();   
        }  

        displaytiktokUser();
    }

    @FXML
    private void deletetiktokUser(ActionEvent event) {

        tiktokUsers selectedtiktokUsers = usersTable.getSelectionModel().getSelectedItem();

        if (selectedtiktokUsers == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No account selected");
            alert.showAndWait();
            return;
        }

        String userName = selectedtiktokUsers.getUserNameID();

        if (DatabaseHandler.deleteTikTokUser(selectedtiktokUsers)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Account Deleted");
            alert.showAndWait();
            
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Account cannot be deleted");
            alert.showAndWait();
        }

        displaytiktokUser();
        displaytiktokUsersPost();
        displayUserFollowTable();
    }

    @FXML
    private void updatetiktokUser(ActionEvent event) {
        tiktokUsers selectedtiktokUsers = usersTable.getSelectionModel().getSelectedItem();
    
        if (selectedtiktokUsers == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No account selected");
            alert.showAndWait();
            return;
        }
    
        String Fname = updateFirstNameField.getText();
        String Lname = updateLastNameField.getText();
        String email = updateEmailField.getText();
        String password = updateUserPasswordField.getText();
        String pronoun = updatePronounField.getText();
    
        Fname = Fname.trim();
        Lname = Lname.trim();
        email = email.trim();
        password = password.trim();
        pronoun = pronoun.trim();
    
 
        if (Fname.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Empty First Name");
            alert.showAndWait();
            return;
        }

        if (!Fname.matches("[a-zA-Z ]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("First Name must contain only alphabetic characters");
            alert.showAndWait();
            return;
        }
    
        if (Lname.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Empty Last Name");
            alert.showAndWait();
            return;
        }

        if (!Lname.matches("[a-zA-Z]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Last Name must contain only alphabetic characters");
            alert.showAndWait();
            return;
        }
    
        if (email.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Empty Email");
            alert.showAndWait();
            return;
        }

        if (!email.endsWith("@gmail.com")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Email must end with '@gmail.com'");
            alert.showAndWait();
            return;
        }
    
        if (password.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Empty Password");
            alert.showAndWait();
            return;
        }
    
        if (pronoun.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Empty Pronoun");
            alert.showAndWait();
            return;
        }

        String lowerCasePronoun = pronoun.toLowerCase();
        if (!lowerCasePronoun.equals("he/him") && !lowerCasePronoun.equals("she/her") && !lowerCasePronoun.equals("they/them")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Pronoun must be 'He/Him', 'She/Her', or 'They/Them'");
            alert.showAndWait();
            return;
        }
    
        selectedtiktokUsers.setFirstName(Fname);
        selectedtiktokUsers.setLastName(Lname);
        selectedtiktokUsers.setEmail(email);
        selectedtiktokUsers.setUserPassword(password);
        selectedtiktokUsers.setPronoun(pronoun);
    
        if (DatabaseHandler.updateTikTokUser(selectedtiktokUsers)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Account Updated");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Account cannot be updated");
            alert.showAndWait();
        }
    
        displaytiktokUser();
    }

    private ObservableList<tiktokUsersPost> getTikTokUsersPost() {
        return tiktokUsersPost;
    }

    public void displaytiktokUsersPost () {
        ResultSet result = DatabaseHandler.getTikTokUsersPost();
        tiktokUsersPost.clear();

        try {
            while (result.next()) {
                int userPostID = result.getInt("PostID");
                String userNameID = result.getString("UserNameID");
                String userPost = result.getString("Post");
                String userPostDate = result.getString("PostDate");

                tiktokUsersPost selectedtiktokUsersPost = new tiktokUsersPost(userPostID, userNameID, userPost, userPostDate);
                tiktokUsersPost.add(selectedtiktokUsersPost);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        usersPostTable.setItems(tiktokUsersPost);
        usersPostTable.refresh();
    
    }

    @FXML
    private void deletetiktokUsersPost(ActionEvent event) {
    tiktokUsersPost selectedtiktokUsersPost = usersPostTable.getSelectionModel().getSelectedItem();

    if (selectedtiktokUsersPost == null) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("No post selected");
        alert.showAndWait();
        return;
    }

    int userPostID = selectedtiktokUsersPost.getUserPostID();

    if (DatabaseHandler.getInstance().deleteTikTokUserPost(selectedtiktokUsersPost)) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("Post Deleted");
        alert.showAndWait();
    } else {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("Post cannot be deleted");
        alert.showAndWait();
    }

    usersPostTable.setItems(getTikTokUsersPost());
    usersPostTable.refresh();
    displaytiktokUsersPost();
}

    @FXML
    private void updatetiktokUsersPost(ActionEvent event) {
    tiktokUsersPost selectedtiktokUsersPost = usersPostTable.getSelectionModel().getSelectedItem();

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

    selectedtiktokUsersPost.setUserPost(userPost);

    if (DatabaseHandler.getInstance().updateTikTokUserPost(selectedtiktokUsersPost)) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("Post Updated");
        alert.showAndWait();
    } else {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("Post cannot be updated");
        alert.showAndWait();
    }

    displaytiktokUsersPost();

    }
 
    private ObservableList<tiktokUserFollow> getTikTokUserFollows() {
        ObservableList<tiktokUserFollow> followList = FXCollections.observableArrayList();
        ResultSet result = DatabaseHandler.getTikTokUserFollows();
    
        try {
            while (result.next()) {
                int followID = result.getInt("FollowID");
                String userNameID = result.getString("UserNameID");
                String followUserNameID = result.getString("FollowUserNameID");
    
                tiktokUserFollow follow = new tiktokUserFollow(followID, userNameID, followUserNameID);
                followList.add(follow);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return followList;
    }

    @FXML
    private void deletetiktokUserFollow(ActionEvent event) {
        tiktokUserFollow selectedFollow = userFollowUserTable.getSelectionModel().getSelectedItem();

        if (selectedFollow == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No follow selected");
            alert.showAndWait();
            return;
        }

        if (DatabaseHandler.getInstance().deleteTikTokUserFollow(selectedFollow)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Follow Deleted");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Follow cannot be deleted");
            alert.showAndWait();
        }

        userFollowUserTable.setItems(getTikTokUserFollows());
        userFollowUserTable.refresh();
        // displaytiktokUsersPost();
    }
}

