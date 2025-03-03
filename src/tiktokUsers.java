import javafx.beans.property.SimpleStringProperty;

public class tiktokUsers {
    private final SimpleStringProperty UserNameID;
    private final SimpleStringProperty FirstName;
    private final SimpleStringProperty LastName;
    private final SimpleStringProperty Email;
    private final SimpleStringProperty UserPassword;
    private final SimpleStringProperty Pronoun;
    private final SimpleStringProperty UserFollowing;
    private final SimpleStringProperty UserFollowers;

    public tiktokUsers(String UserNameID, String FirstName, String LastName, String Email, String UserPassword, String Pronoun, int UserFollowing, int UserFollowers) {
        this.UserNameID = new SimpleStringProperty(UserNameID);
        this.FirstName = new SimpleStringProperty(FirstName);
        this.LastName = new SimpleStringProperty(LastName);
        this.Email = new SimpleStringProperty(Email);
        this.UserPassword = new SimpleStringProperty(UserPassword);
        this.Pronoun = new SimpleStringProperty(Pronoun);
        this.UserFollowing = new SimpleStringProperty(String.valueOf(UserFollowing));
        this.UserFollowers = new SimpleStringProperty(String.valueOf(UserFollowers));
    }

    public String getUserNameID() {
        return this.UserNameID.get();
    }

    public String getFirstName() {
        return this.FirstName.get();
    }

    public String getLastName() {
        return this.LastName.get();
    }

    public String getEmail() {
        return this.Email.get();
    }

    public String getUserPassword() {
        return this.UserPassword.get();
    }

    public String getPronoun() {
        return this.Pronoun.get();
    }

    public String getUserFollowing() {
        return this.UserFollowing.get();
    }

    public String getUserFollowers() {
        return this.UserFollowers.get();
    }

    public void setUserNameID(String UserNameID) {
        this.UserNameID.set(UserNameID);
    }

    public void setFirstName(String FirstName) {
        this.FirstName.set(FirstName);
    }

    public void setLastName(String LastName) {
        this.LastName.set(LastName);
    }

    public void setEmail(String Email) {
        this.Email.set(Email);
    }

    public void setUserPassword(String UserPassword) {
        this.UserPassword.set(UserPassword);
    }

    public void setPronoun(String Pronoun) {
        this.Pronoun.set(Pronoun);
    }

    public void setUserFollowing(String UserFollowing) {
        this.UserFollowing.set(UserFollowing);
    }

    public void setUserFollowers(String UserFollowers) {
        this.UserFollowers.set(UserFollowers);
    }
}
