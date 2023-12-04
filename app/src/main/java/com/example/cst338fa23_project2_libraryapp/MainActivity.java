package com.example.cst338fa23_project2_libraryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.example.cst338fa23_project2_libraryapp.DB.AppDatabase;
import com.example.cst338fa23_project2_libraryapp.DB.User;
//import com.example.cst338fa23_project2_libraryapp.DB.UserDAO;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText mUsernameField, mPasswordField; // EditText Fields from activity_main
    private Button mLoginButton; // Button from activity_main
    private String mUsername, mPassword; // String Values that come from the EditText Fields
    List<User> mUserList = new ArrayList<>(); // List of Users (Was Testing before DAO implementation)
    //private UserDAO mUserDAO; Commented Out DAO [Implementation wasn't successful]
    User mUser; // User object

    // Default Admin User Similar to the pre-defined ones mentioned in previous parts of the project
    User mDefaultAdminUser = new User(1, "testuser1", "testuser1", false);

    // Default Test User Similar to the pre-defined ones mentioned in previous parts of the project
    User mDefaultTestUser = new User(2, "admin2", "admin2", true);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Sets the content view to activity_main.xml layout

        // Commented out DAO Work
        /*AppDatabase mAppDatabase = AppDatabase.getInstance(this);
        mUserDAO = mAppDatabase.UserDAO(); // Sets the Database

        // AsyncTask
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mUserDAO.insert(mDefaultTestUser); Inserts User in the Background
                mUserDAO.insert(mDefaultAdminUser); Inserts User in the Background
                return null;
            }

            protected void onPostExecute(Void result) {
                //AppDatabase.clearDatabase(mAppDatabase); After executing, clears the database [Commented Out, so it didn't happen all the time]
                getDatabase(); After executing, gets the database
                wireUpDisplayDAO(); After executing calls the wireUpDisplayDAO method
            }
        }.execute(); Executes the AsyncTask */


        mUserList.add(mDefaultTestUser); // Adds Default Test User to List of Users [Before DAO implementation]
        mUserList.add(mDefaultAdminUser); // Adds Default Admin User to List of Users [Before DAO implementation]
        wireUpDisplay(); // Calls wireUpDisplay method
    }

    // Commented Out wireUpDisplayDAO method
    /*private void wireUpDisplayDAO() {
        mUsernameField = findViewById(R.id.editTextLoginUserName);
        mPasswordField = findViewById(R.id.editTextLoginPassword);
        mLoginButton = findViewById(R.id.buttonLogin);

        // Executes when the login button on the layout is clicked
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValuesFromDisplay(); // Sets the EditText values to the String fields

                // Boolean Method Checking if the User Existed in the DB
                if (checkForUserInDatabase()) {
                    // If the User Existed -> Validated if the Password was Correct
                    // Wrong Password
                    if (!validatePassword()) {
                        mPasswordField.setError("Invalid Password. Please Try Again");
                    }

                    // Correct Password
                    else {
                        // Checks if the User is an Admin | Was Meant for Testing
                        if (mUser.isAdmin()) {
                            Intent intent = LandingPage.intentFactory(getApplicationContext(),
                                mUser.getUserId());
                            startActivity(intent); // Starts LandingPageActivity
                        }

                        // Checks if the User is Normal | Was Meant for Testing
                        else {
                            Intent intent = LandingPage.intentFactory(getApplicationContext(),
                                mUser.getUserId());
                            startActivity(intent); // Starts LandingPageActivity
                        }
                    }
            }
        });
    }*/

    private void wireUpDisplay() {
        mUsernameField = findViewById(R.id.editTextLoginUserName);
        mPasswordField = findViewById(R.id.editTextLoginPassword);
        mLoginButton = findViewById(R.id.buttonLogin);

        // Executes when the login button on the layout is clicked
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValuesFromDisplay(); // Sets the EditText values to the String fields
                mUser = checkForUserInList(); // Sets mUser to a potential existing user
                // Positive | User Exists!
                if (mUser != null) {
                    // Validates the Password of the User
                    // Positive | Password is Correct!
                    if (validatePassword()) {
                        Intent intent = LandingPage.intentFactory(getApplicationContext(), mUser);
                        startActivity(intent);
                    }

                    // Negative | Password is NOT Correct
                    else {
                        mPasswordField.setError("Invalid Password. Please Try Again");
                    }
                }
            }
        });
    }

    private void getValuesFromDisplay() {
        mUsername = mUsernameField.getText().toString(); // Sets Text from EditText Field
        mPassword = mPasswordField.getText().toString(); // Sets Text from EditText Field
    }

    public static Intent intentFactory(Context packageContext) {
        Intent intent = new Intent(packageContext, MainActivity.class);
        return intent;
    }

    public User checkForUserInList() {
        User mUser = null; // Sets mUser obj to null
        // For Each Loop to Iterate Through All the Users in the List
        for (User user: mUserList) {
            // If the EditText Username is the Same as a User in the List
            if (mUsername.equals(user.getUsername())) {
                mUser = user; // Sets the current user in the list to mUser
            }
        }

        // If mUser obj is null | User doesn't exist
        if (mUser == null) {
            // Toast Message
            Toast.makeText(MainActivity.this, "No user: " + mUsername + " found",
                    Toast.LENGTH_SHORT).show();
            return null; // Returns null
        }

        return mUser; // Returns mUser
    }

    // Checks if the User exists in the List
    public boolean checkForUserInList(String username) {
        boolean mAlreadyUsed = false; // Defaults to False
        // For Each Loop to Iterate Through All the Users in the List
        for (User user: mUserList) {
            // If the EditText Username is the Same as a User in the List
            if (username.equals(user.getUsername())) {
                mAlreadyUsed = true; // Sets value to true
            }
        }

        return mAlreadyUsed; // Returns boolean value
    }

    // Checks if the User exists in the Database
    /*private boolean checkForUserInDatabase() {
        mUser = mUserDAO.getUserByUsername(mUsername); // Gets the User by their Username from the EditText Field Username
        // If mUser is null
        if (mUser == null) {
            mUsernameField.setError("No user: " + mUsername + " found"); // Indicates error
            return false; // Returns false
        }

        return true; // Returns true
    }*/

    // Method to Return the List of User Size
    public int getUserListSize() {return mUserList.size();}

    // Validates Password of Users
    public boolean validatePassword() {
        return mUser.getPassword().equals(mPassword); // Returns Value based on whether the password of the User matches the EditText Field Password
    }

    // Validates Password of a User w/ Params
    public boolean validatePassword(String username, String password) {
        boolean mCorrectPassword = false; // Default Value
        // For Each Loop to Iterate Through All the Users in the List
        for (User user: mUserList) {
            // If the Username of the User equals the username parameter
            if (username.equals(user.getUsername())) {
                // If the Password of the User equals the password parameter
                if (user.getPassword().equals(password)) {
                    mCorrectPassword = true; // Updates Value to True
                }
            }
        }

        return mCorrectPassword; // Returns boolean value
    }

    // Adds User to the List of Users
    public void addUserToList(User user) {mUserList.add(user);}

    // Deletes User from the List of Users
    public void deleteUserFromList(String password) {
        for (User user: mUserList) {
            if (password.equals(user.getPassword())) {
                mUserList.remove(user);
            }
        }
    }

    // Sets mUserDAO to the Database
    /*private void getDatabase() {
        mUserDAO = AppDatabase.getInstance(this).UserDAO();
    }*/
}