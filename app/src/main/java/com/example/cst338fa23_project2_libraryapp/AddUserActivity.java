package com.example.cst338fa23_project2_libraryapp;

import androidx.appcompat.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.example.cst338fa23_project2_libraryapp.DB.AppDatabase;
import com.example.cst338fa23_project2_libraryapp.DB.User;
//import com.example.cst338fa23_project2_libraryapp.DB.UserDAO;
import com.example.myapplication.R;

public class AddUserActivity extends MainActivity {

    private Button mReturnButton, mCreateAccountButton; // Buttons from Layout
    private EditText mUsernameField, mPasswordField; // EditText Fields from Layout
    private User mUser; // mUser obj | Used to create new account if everything is successful
    private String mUsername, mPassword; // Strings to extract values from EditText Fields
    //private UserDAO mUserDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user); // Sets content view to activity_add_user.xml
        //getDatabase();
        wireUpDisplay(); // Calls the wireUpDisplay method

        // When the Return Button is Pressed
        mReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creates Alert Dialogue
                AlertDialog.Builder mReturnToMainAlert = new AlertDialog.Builder(AddUserActivity.this);
                mReturnToMainAlert.setTitle("Return to Main Page");
                mReturnToMainAlert.setMessage("Would you like to cancel creating an account, " +
                        "and return to the main page?");
                mReturnToMainAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = LandingPage.intentFactory(getApplicationContext());
                        startActivity(intent); // Returns to LandingPage
                        Toast.makeText(AddUserActivity.this,
                                "Returning to the main page...", Toast.LENGTH_SHORT).show();
                    }
                });

                mReturnToMainAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Makes Toast, Stays on AddUserActivity
                        Toast.makeText(AddUserActivity.this,
                                "Continuing to create account...", Toast.LENGTH_SHORT).show();
                    }
                });

                mReturnToMainAlert.show();
            }
        });

        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    // Creates Alert Dialogue
                    AlertDialog.Builder mAccountPrivileges = new AlertDialog.Builder(AddUserActivity.this);
                    mAccountPrivileges.setTitle("Normal or Admin");
                    mAccountPrivileges.setMessage("Which type of account will this be?");
                    // Pressed if the User is Going to be an Admin
                    mAccountPrivileges.setPositiveButton("Admin", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getValuesFromDisplay(); // Stores values from EditText field to String fields
                            // If the User already Exists
                            if (AddUserActivity.super.checkForUserInList(mUsername)) {
                                mUsernameField.setError("Username Already Exists. " +
                                        "Please try another username"); // Error Indicating Username is already used
                            }

                            // Criteria that Username cannot exceed 10 characters
                            else if (mUsername.length() > 10) {
                                mUsernameField.setError("Username is " + mUsername.length() +
                                        ". Please try again with a username under 10 characters");
                            }

                            // Criteria that Password cannot exceed 10 characters
                            else if (mPassword.length() > 10) {
                                mPasswordField.setError("Username is " + mPassword.length() +
                                        ". Please try again with a username under 10 characters");
                            }

                            // If the Username & Password meet the criteria & User doesn't already exist
                            else if (!AddUserActivity.super.checkForUserInList(mUsername)) {
                                mUser = new User(AddUserActivity.super.getUserListSize() + 1, mUsername, mPassword, true);
                                AddUserActivity.super.addUserToList(mUser); // Adds User to list
                                //mUserDAO.insert(mUser);
                                Toast.makeText(AddUserActivity.this, "Admin Account " +
                                        "Successfully Created", Toast.LENGTH_SHORT).show();
                                Intent intent = LandingPage.intentFactory(getApplicationContext());
                                startActivity(intent); // Returns to Landing Page
                            }
                        }
                    });

                    // Pressed if the User is Going to be a Normal
                    mAccountPrivileges.setNegativeButton("Normal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getValuesFromDisplay(); // Stores values from EditText field to String fields
                            // If the User already Exists
                            if (AddUserActivity.super.checkForUserInList(mUsername)) {
                                mUsernameField.setError("Username Already Exists. " +
                                        "Please try another username");
                            }

                            // Criteria that Username cannot exceed 10 characters
                            else if (mUsername.length() > 10) {
                                mUsernameField.setError("Username length is " + mUsername.length() +
                                        ". Please try again with a username under 10 characters");
                            }

                            // Criteria that Password cannot exceed 10 characters
                            else if (mPassword.length() > 10) {
                                mPasswordField.setError("Password length is " + mPassword.length() +
                                        ". Please try again with a username under 10 characters");
                            }

                            // If the Username String is Empty / Nothing Inputted into EditText Field
                            else if (mUsername.isEmpty()) {
                                mUsernameField.setError("Username field is empty");
                            }

                            // If the Password String is Empty / Nothing Inputted into EditText Field
                            else if (mPassword.isEmpty()) {
                                mPasswordField.setError("Password field is empty");
                            }

                            // If the Username & Password meet the criteria & User doesn't already exist
                            else if (!AddUserActivity.super.checkForUserInList(mUsername)) {
                                mUser = new User(AddUserActivity.super.getUserListSize() + 1, mUsername, mPassword, false);
                                AddUserActivity.super.addUserToList(mUser); // Adds User to list
                                //mUserDAO.insert(mUser);
                                Toast.makeText(AddUserActivity.this, "Normal Account " +
                                        "Successfully Created", Toast.LENGTH_SHORT).show();
                                Intent intent = LandingPage.intentFactory(getApplicationContext());
                                startActivity(intent); // Returns to LandingPage
                            }
                        }
                    });

                    mAccountPrivileges.show();
                }
            });
    }

    private void wireUpDisplay() {
        mReturnButton = findViewById(R.id.addUserReturnButton);
        mCreateAccountButton = findViewById(R.id.addUserCreateButton);
        mUsernameField = findViewById(R.id.addUserUsernameEditText);
        mPasswordField = findViewById(R.id.addUserPasswordEditText);
    }

    public static Intent intentFactory(Context packageContext) {
        Intent intent = new Intent(packageContext, AddUserActivity.class);
        return intent;
    }

    private void getValuesFromDisplay() {
        mUsername = mUsernameField.getText().toString();
        mPassword = mPasswordField.getText().toString();
    }

    /*private void getDatabase() {
        mUserDAO = AppDatabase.getInstance(this).UserDAO();
    }*/
}