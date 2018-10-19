package com.app.android.file_management.database;

public class UserInfo {
    private String userName;
    private String pass;

    public UserInfo(String userName, String pass) {
        this.userName = userName;
        this.pass = pass;
    }

    public UserInfo() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
