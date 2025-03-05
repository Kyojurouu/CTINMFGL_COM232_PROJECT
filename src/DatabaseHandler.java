import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DatabaseHandler {

    private static DatabaseHandler handler = null;
    private static Statement stmt = null;
    private static PreparedStatement pstatement = null;

    public static DatabaseHandler getInstance() {
        if (handler == null) {
            handler = new DatabaseHandler();
        }
        return handler;
    }

    public static Connection getDBConnection()
    {
        Connection connection = null;
        String dburl = "jdbc:mysql://localhost:3306/TikTokDB?serverTimezone=Asia/Kuala_Lumpur";
        String userName = "root";
        String password = "password";

        try
        {
            connection = DriverManager.getConnection(dburl, userName, password);

        } catch (Exception e){
            e.printStackTrace();
        }

        return connection;
    }

    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            stmt = getDBConnection().createStatement();
            result = stmt.executeQuery(query);
        }
        catch (SQLException ex) {
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return null;
        }
        finally {
        }
        return result;
    }

    public static boolean validateLogin(String adminUser, String adminPassword){

        getInstance();
        String query = "SELECT * FROM adminUsers WHERE adminUser = '" + adminUser + "' AND adminPassword = '" + adminPassword + "'";
        
        System.out.println(query);

        ResultSet result = handler.execQuery(query);
        try {
            if (result.next()) {
                return true;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static ResultSet getadminUsers() {
        ResultSet result = null;

        try {
            String query = "SELECT * FROM adminUsers";
            result = handler.execQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
     }

     public static boolean addadminUser(adminUsers adminUser) {
        try {
            pstatement = getDBConnection().prepareStatement("INSERT INTO adminUsers(adminUser, adminPassword) VALUES(?, ?)");
            pstatement.setString(1, adminUser.getAdminUsername());
            pstatement.setString(2, adminUser.getAdminPassword());

            return pstatement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

     }

     public static boolean deleteadminUser(adminUsers adminUser) {
        try {
            pstatement = getDBConnection().prepareStatement("DELETE FROM adminUsers WHERE adminUser = ?");
            pstatement.setString(1, adminUser.getAdminUsername());

            int res = pstatement.executeUpdate();
            if (res > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

     }

     public static boolean updateadminUser(adminUsers adminUser) {
        try {
            pstatement = getDBConnection().prepareStatement("UPDATE adminUsers SET adminUser = ?, adminPassword = ? WHERE id = ?");
            pstatement.setString(1, adminUser.getAdminUsername());
            pstatement.setString(2, adminUser.getAdminPassword());
            pstatement.setInt(3, Integer.parseInt(adminUser.getAdminID()));
            
            int res = pstatement.executeUpdate();
            if (res > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

     }


        public static boolean addTikTokUser(tiktokUsers tiktokUser) {
            try {
                pstatement = getDBConnection().prepareStatement("INSERT INTO tiktokUsers(UserNameID, FirstName, LastName, Email, UserPassword, Pronoun) VALUES(?, ?, ?, ?, ?, ?)");
                pstatement.setString(1, tiktokUser.getUserNameID());
                pstatement.setString(2, tiktokUser.getFirstName());
                pstatement.setString(3, tiktokUser.getLastName());
                pstatement.setString(4, tiktokUser.getEmail());
                pstatement.setString(5, tiktokUser.getUserPassword());
                pstatement.setString(6, tiktokUser.getPronoun());
    
                return pstatement.executeUpdate() > 0;
    
            } catch (Exception e) {
                e.printStackTrace();
            }
    
            return false;
    
         }
    
         public static boolean deleteTikTokUser(tiktokUsers tiktokUser) {
            try {
                pstatement = getDBConnection().prepareStatement("DELETE FROM tiktokUsers WHERE UserNameID = ?");
                pstatement.setString(1, tiktokUser.getUserNameID());
    
                int res = pstatement.executeUpdate();
                if (res > 0) {
                    return true;
                }
    
            } catch (Exception e) {
                e.printStackTrace();
            }
    
            return false;
    
         }

         public static boolean profiledeleteTikTokUser(String tiktokUser) {
            try {
                pstatement = getDBConnection().prepareStatement("DELETE FROM tiktokUsers WHERE UserNameID = ?");
                pstatement.setString(1, tiktokUser);
    
                int res = pstatement.executeUpdate();
                if (res > 0) {
                    return true;
                }
    
            } catch (Exception e) {
                e.printStackTrace();
            }
    
            return false;
    
         }
    
         public static boolean updateTikTokUser(tiktokUsers tiktokUser) {
            try {
                String query = "UPDATE tiktokUsers SET FirstName = ?, LastName = ?, Email = ?, UserPassword = ?, Pronoun = ? WHERE UserNameID = ?";
                PreparedStatement pstatement = getDBConnection().prepareStatement(query);
                pstatement.setString(1, tiktokUser.getFirstName());
                pstatement.setString(2, tiktokUser.getLastName());
                pstatement.setString(3, tiktokUser.getEmail());
                pstatement.setString(4, tiktokUser.getUserPassword());
                pstatement.setString(5, tiktokUser.getPronoun());
                pstatement.setString(6, tiktokUser.getUserNameID());

                int res = pstatement.executeUpdate();
                if (res > 0) {
                    return true;
                }
        
            } catch (Exception e) {
                e.printStackTrace();
            }
        
            return false;
        }

         public static ResultSet getTikTokUsers() {
            ResultSet result = null;
    
            try {
                String query = "SELECT * FROM tiktokUsers";
                result = handler.execQuery(query);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
         }

         public static boolean uservalidateLogin(String UserNameID, String UserPassword) {
            getInstance();
            String query = "SELECT * FROM tiktokUsers WHERE UserNameID = ? AND UserPassword = ?";
            
            try (PreparedStatement pstatement = getDBConnection().prepareStatement(query)) {
                pstatement.setString(1, UserNameID);
                pstatement.setString(2, UserPassword);
                ResultSet result = pstatement.executeQuery();
                
                if (result.next()) {
                    SessionManager.getInstance().setCurrentUserName(UserNameID);
                    SessionManager.getInstance().setFirstName(result.getString("FirstName"));
                    SessionManager.getInstance().setLastName(result.getString("LastName"));
                    SessionManager.getInstance().setEmail(result.getString("Email"));
                    SessionManager.getInstance().setUserPassword(result.getString("UserPassword"));
                    SessionManager.getInstance().setPronoun(result.getString("Pronoun"));
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }
    
        public static ResultSet gettiktokUsers() {
            ResultSet result = null;
    
            try {
                String query = "SELECT * FROM tiktokUsers";
                result = handler.execQuery(query);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
         }

         public String getUsernameById(int userId) {
            String username = null;
            String query = "SELECT UserNameID FROM tiktokUsers WHERE UserNameID = ?";
            try (PreparedStatement preparedStatement = getDBConnection().prepareStatement(query)) {
                preparedStatement.setInt(1, userId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    username = resultSet.getString("UserNameID");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return username;
        }
    
        public boolean addTikTokUserPost(tiktokUsersPost post) {
            String query = "INSERT INTO tiktokUsersPost (UserNameID, Post, PostDate) VALUES (?, ?, NOW())";
            try (PreparedStatement preparedStatement = getDBConnection().prepareStatement(query)) {
                preparedStatement.setString(1, post.getUserNameID());
                preparedStatement.setString(2, post.getUserPost());
                int result = preparedStatement.executeUpdate();
                return result > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        public static ResultSet getTikTokUsersPost() {
            ResultSet result = null;
    
            try {
                String query = "SELECT * FROM tiktokUsersPost";
                result = handler.execQuery(query);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
        

        public boolean deleteTikTokUserPost(tiktokUsersPost post) {
            String query = "DELETE FROM tiktokUsersPost WHERE PostID = ?";
            try (PreparedStatement preparedStatement = getDBConnection().prepareStatement(query)) {
                preparedStatement.setInt(1, post.getUserPostID());
                int result = preparedStatement.executeUpdate();
                return result > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        public boolean updateTikTokUserPost(tiktokUsersPost post) {
            String query = "UPDATE tiktokUsersPost SET Post = ?, PostDate = ? WHERE PostID = ?";
            try (PreparedStatement preparedStatement = getDBConnection().prepareStatement(query)) {
                preparedStatement.setString(1, post.getUserPost());
                preparedStatement.setString(2, post.getUserPostDate());
                preparedStatement.setInt(3, post.getUserPostID());
                int result = preparedStatement.executeUpdate();
                return result > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        public static ResultSet getTikTokUsersPostByUserName(String userNameID) {
            ResultSet result = null;
            try {
                String query = "SELECT * FROM tiktokUsersPost WHERE UserNameID = '" + userNameID + "'";
                result = handler.execQuery(query);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        public boolean updateTikTokUsersPost(String userPost, int userPostID, String userNameID) {
            String query = "UPDATE tiktokUsersPost SET Post = ? WHERE PostID = ? AND UserNameID = ?";
            try (PreparedStatement preparedStatement = getDBConnection().prepareStatement(query)) {
                preparedStatement.setString(1, userPost);
                preparedStatement.setInt(2, userPostID);
                preparedStatement.setString(3, userNameID);
                int result = preparedStatement.executeUpdate();
                return result > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    
        public boolean deleteTikTokUsersPost(int userPostID, String userNameID) {
            String query = "DELETE FROM tiktokUsersPost WHERE PostID = ? AND UserNameID = ?";
            try (PreparedStatement preparedStatement = getDBConnection().prepareStatement(query)) {
                preparedStatement.setInt(1, userPostID);
                preparedStatement.setString(2, userNameID);
                int result = preparedStatement.executeUpdate();
                return result > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }


        public tiktokUsers getUserDetails(String userNameID) {
        tiktokUsers user = null;
        String query = "SELECT * FROM tiktokUsers WHERE UserNameID = ?";
    
    try (Connection connection = getDBConnection();
         PreparedStatement pstatement = connection.prepareStatement(query)) {
        
        pstatement.setString(1, userNameID);
        ResultSet resultSet = pstatement.executeQuery();
        
        if (resultSet.next()) {
            String firstName = resultSet.getString("FirstName");
            String lastName = resultSet.getString("LastName");
            String email = resultSet.getString("Email");
            String password = resultSet.getString("UserPassword");
            String pronoun = resultSet.getString("Pronoun");
            int followers = resultSet.getInt("UserFollowers");
            int following = resultSet.getInt("UserFollowing");
            
            user = new tiktokUsers(userNameID, firstName, lastName, email, password, pronoun, followers, following);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return user;
}

    public static ResultSet getTikTokUserFollows() {
    String query = "SELECT * FROM tiktokUserFollow";
    try {
        return handler.execQuery(query);
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}

       public boolean userExists(String userNameID) {
        String query = "SELECT COUNT(*) FROM tiktokUsers WHERE UserNameID = ?";
        try (PreparedStatement preparedStatement = getDBConnection().prepareStatement(query)) {
            preparedStatement.setString(1, userNameID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean followExists(String userNameID, String followUserNameID) {
        String query = "SELECT COUNT(*) FROM tiktokUserFollow WHERE UserNameID = ? AND FollowUserNameID = ?";
        try (Connection connection = getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userNameID);
            preparedStatement.setString(2, followUserNameID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addTikTokUserFollow(tiktokUserFollow follow) {
        String followQuery = "INSERT INTO tiktokUserFollow (UserNameID, FollowUserNameID) VALUES (?, ?)";
        // String incrementFollowingQuery = "UPDATE tiktokUsers SET UserFollowing = UserFollowing + 1 WHERE UserNameID = ?";
        // String incrementFollowersQuery = "UPDATE tiktokUsers SET UserFollowers = UserFollowers + 1 WHERE UserNameID = ?";
        
        try (Connection connection = getDBConnection()) {
            connection.setAutoCommit(false);
    
            try (PreparedStatement followStatement = connection.prepareStatement(followQuery);
                //  PreparedStatement incrementFollowingStatement = connection.prepareStatement(incrementFollowingQuery);
                //  PreparedStatement incrementFollowersStatement = connection.prepareStatement(incrementFollowersQuery)
                 
                 ) 
                 {
    
                followStatement.setString(1, follow.getUserNameID());
                followStatement.setString(2, follow.getFollowUserNameID());
                followStatement.executeUpdate();
    
                // incrementFollowingStatement.setString(1, follow.getUserNameID());
                // incrementFollowingStatement.executeUpdate();
    
                // incrementFollowersStatement.setString(1, follow.getFollowUserNameID());
                // incrementFollowersStatement.executeUpdate();
    
                connection.commit();
                // System.out.println("Follow added successfully and counts updated.");
                return true;
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTikTokUserFollow(tiktokUserFollow follow) {
        String deleteFollowQuery = "DELETE FROM tiktokUserFollow WHERE UserNameID = ? AND FollowUserNameID = ?";
        // String decrementFollowingQuery = "UPDATE tiktokUsers SET UserFollowing = UserFollowing - 1 WHERE UserNameID = ?";
        // String decrementFollowersQuery = "UPDATE tiktokUsers SET UserFollowers = UserFollowers - 1 WHERE UserNameID = ?";
    
        try (Connection connection = getDBConnection()) {
            connection.setAutoCommit(false);
    
            try (PreparedStatement deleteFollowStmt = connection.prepareStatement(deleteFollowQuery);
                //  PreparedStatement decrementFollowingStmt = connection.prepareStatement(decrementFollowingQuery);
                //  PreparedStatement decrementFollowersStmt = connection.prepareStatement(decrementFollowersQuery)
                 
                 ) {
    
                deleteFollowStmt.setString(1, follow.getUserNameID());
                deleteFollowStmt.setString(2, follow.getFollowUserNameID());
                // decrementFollowingStmt.setString(1, follow.getUserNameID());
                // decrementFollowersStmt.setString(1, follow.getFollowUserNameID());
    
                deleteFollowStmt.executeUpdate();
                // decrementFollowingStmt.executeUpdate();
                // decrementFollowersStmt.executeUpdate();
    
                connection.commit();
                return true;
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

        public ObservableList<tiktokUserFollow> getTikTokUserFollowByUserName(String userNameID) {
            ObservableList<tiktokUserFollow> followList = FXCollections.observableArrayList();
            String query = "SELECT * FROM tiktokUserFollow WHERE UserNameID = ?";
            try (Connection connection = getDBConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, userNameID);
                ResultSet resultSet = preparedStatement.executeQuery();
    
                while (resultSet.next()) {
                    int followID = resultSet.getInt("FollowID");
                    String userNameIDResult = resultSet.getString("UserNameID");
                    String followUserNameID = resultSet.getString("FollowUserNameID");
    
                    tiktokUserFollow follow = new tiktokUserFollow(followID, userNameIDResult, followUserNameID);
                    followList.add(follow);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return followList;
        }
    
        public ObservableList<tiktokUserFollow> getTikTokUserFollowersByUserName(String userNameID) {
            ObservableList<tiktokUserFollow> followerList = FXCollections.observableArrayList();
            String query = "SELECT * FROM tiktokUserFollow WHERE FollowUserNameID = ?";
            try (Connection connection = getDBConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, userNameID);
                ResultSet resultSet = preparedStatement.executeQuery();
    
                while (resultSet.next()) {
                    int followID = resultSet.getInt("FollowID");
                    String userNameIDResult = resultSet.getString("UserNameID");
                    String followUserNameID = resultSet.getString("FollowUserNameID");
    
                    tiktokUserFollow follow = new tiktokUserFollow(followID, userNameIDResult, followUserNameID);
                    followerList.add(follow);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return followerList;
        }
        
     }     
