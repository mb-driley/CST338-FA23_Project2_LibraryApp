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

import com.example.cst338fa23_project2_libraryapp.DB.User;

public class AddUserActivity extends MainActivity {

    private Button mReturnButton, mCreateAccountButton;
    private EditText mUsernameField, mPasswordField;
    private User mUser;
    private String mUsername, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        wireUpDisplay();

        mReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mReturnToMainAlert = new AlertDialog.Builder(AddUserActivity.this);
                mReturnToMainAlert.setTitle("Return to Main Page");
                mReturnToMainAlert.setMessage("Would you like to cancel creating an account, " +
                        "and return to the main page?");
                mReturnToMainAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = LandingPage.intentFactory(getApplicationContext());
                        startActivity(intent);
                        Toast.makeText(AddUserActivity.this,
                                "Returning to the main page...", Toast.LENGTH_SHORT).show();
                    }
                });

                mReturnToMainAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
                    AlertDialog.Builder mAccountPrivileges = new AlertDialog.Builder(AddUserActivity.this);
                    mAccountPrivileges.setTitle("Normal or Admin");
                    mAccountPrivileges.setMessage("Which type of account will this be?");
                    mAccountPrivileges.setPositiveButton("Admin", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getValuesFromDisplay();
                            if (AddUserActivity.super.checkForUserInList(mUsername)) {
                                mUsernameField.setError("Username Already Exists. " +
                                        "Please try another username");
                            }

                            else if (mUsername.length() > 10) {
                                mUsernameField.setError("Username is " + mUsername.length() +
                                        ". Please try again with a username under 10 characters");
                            }

                            else if (mPassword.length() > 10) {
                                mPasswordField.setError("Username is " + mPassword.length() +
                                        ". Please try again with a username under 10 characters");
                            }

                            else if (!AddUserActivity.super.checkForUserInList(mUsername)) {
                                mUser = new User(AddUserActivity.super.getUserListSize() + 1, mUsername, mPassword, true);
                                AddUserActivity.super.addUserToList(mUser);
                                Toast.makeText(AddUserActivity.this, "Admin Account " +
                                        "Successfully Created", Toast.LENGTH_SHORT).show();
                                Intent intent = LandingPage.intentFactory(getApplicationContext());
                                startActivity(intent);
                            }
                        }
                    });

                    mAccountPrivileges.setNegativeButton("Normal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getValuesFromDisplay();
                            if (AddUserActivity.super.checkForUserInList(mUsername)) {
                                mUsernameField.setError("Username Already Exists. " +
                                        "Please try another username");
                            }

                            else if (mUsername.length() > 10) {
                                mUsernameField.setError("Username length is " + mUsername.length() +
                                        ". Please try again with a username under 10 characters");
                            }

                            else if (mPassword.length() > 10) {
                                mPasswordField.setError("Password length is " + mPassword.length() +
                                        ". Please try again with a username under 10 characters");
                            }

                            else if (mUsername.isEmpty()) {
                                mUsernameField.setError("Username field is empty");
                            }

                            else if (mPassword.isEmpty()) {
                                mPasswordField.setError("Password field is empty");
                            }

                            else if (!AddUserActivity.super.checkForUserInList(mUsername)) {
                                mUser = new User(AddUserActivity.super.getUserListSize() + 1, mUsername, mPassword, false);
                                AddUserActivity.super.addUserToList(mUser);
                                Toast.makeText(AddUserActivity.this, "Normal Account " +
                                        "Successfully Created", Toast.LENGTH_SHORT).show();
                                Intent intent = LandingPage.intentFactory(getApplicationContext());
                                startActivity(intent);
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
}