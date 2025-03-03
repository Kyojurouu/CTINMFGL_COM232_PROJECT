public class tiktokUserFollow {

        private int followID;
        private String userNameID;
        private String followUserNameID;
    
        public tiktokUserFollow(int followID, String userNameID, String followUserNameID) {
            this.followID = followID;
            this.userNameID = userNameID;
            this.followUserNameID = followUserNameID;
        }
    
        public int getFollowID() {
            return followID;
        }
    
        public String getUserNameID() {
            return userNameID;
        }
    
        public String getFollowUserNameID() {
            return followUserNameID;
        }
    
}
