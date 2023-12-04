package com.example.cst338fa23_project2_libraryapp.DB;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int mUserId;
    private String mUsername;
    private String mPassword;
    private boolean mCurrentlyRenting;
    private boolean mAdmin;

    public User(int userId, String username, String password, boolean admin) {
        mUserId = userId;
        mUsername = username;
        mPassword = password;
        mCurrentlyRenting = false;
        mAdmin = admin;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
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

    public boolean isCurrentlyRenting() {
        return mCurrentlyRenting;
    }

    public void setCurrentlyRenting(boolean currentlyRenting) {
        mCurrentlyRenting = currentlyRenting;
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
                "UserId=" + mUserId +
                ", Username='" + mUsername + '\'' +
                ", Password='" + mPassword + '\'' +
                ", CurrentlyRenting=" + mCurrentlyRenting +
                ", Admin=" + mAdmin +
                '}';
    }
}
