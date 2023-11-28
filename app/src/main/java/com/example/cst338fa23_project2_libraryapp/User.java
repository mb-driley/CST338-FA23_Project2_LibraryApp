package com.example.cst338fa23_project2_libraryapp;



public class User {
    private String mUsername, mPassword;
    private boolean mAdmin;

    public User(String username, String password, boolean admin) {
        mUsername = username;
        mPassword = password;
        mAdmin = admin;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public boolean isAdmin() {
        return mAdmin;
    }

    public void setAdmin(boolean admin) {
        mAdmin = admin;
    }

    @Override
    public String toString() {
        return "User{" +
                "Username='" + mUsername + '\'' +
                ", Password='" + mPassword + '\'' +
                ", Admin=" + mAdmin +
                '}';
    }
}
