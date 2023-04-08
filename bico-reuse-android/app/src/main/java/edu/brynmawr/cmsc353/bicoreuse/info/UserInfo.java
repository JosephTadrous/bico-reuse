package edu.brynmawr.cmsc353.bicoreuse.info;

public class UserInfo {
    private String userId;
    private String userName;
    private String userCollege;
    private String userEmail;
    private String userPhone;

    public UserInfo(String id, String name, String college, String email, String phone) {
        userId= id;
        userName= name;
        userCollege= college;
        userEmail= email;
        userPhone= phone;
    }


    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserCollege() {
        return userCollege;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
