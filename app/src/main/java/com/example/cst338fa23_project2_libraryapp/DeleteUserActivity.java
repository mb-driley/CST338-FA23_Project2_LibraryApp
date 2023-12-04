package com.example.cst338fa23_project2_libraryapp;

import androidx.appcompat.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.cst338fa23_project2_libraryapp.DB.AppDatabase;
import com.example.cst338fa23_project2_libraryapp.DB.User;
//import com.example.cst338fa23_project2_libraryapp.DB.UserDAO;
import com.example.myapplication.R;

public class DeleteUserActivity extends MainActivity {
    private EditText mInputTextField; // EditText Field from Layout
    private String mSavedUsername, mInputtedText; // Strings to save EditText Field inputs
    private Button mReturnButton, mContinueButton; // Buttons from Layout
    private TextView mInstructionTextView; // TextView from Layout
    //private UserDAO mUserDAO;
    //private User mDeletedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user); // Sets context view to activity_delete_user.xml
        //getDatabase(); Commented Out | gets User database
        wireUpDisplay(); // Calls wireUpDisplay method

        // Acts Like AddUserActivity
        mReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creates Alert Dialogue
                AlertDialog.Builder mReturnToMainAlert = new AlertDialog.Builder(DeleteUserActivity.this);
                mReturnToMainAlert.setTitle("Return to Main Page");
                mReturnToMainAlert.setMessage("Would you like to cancel deleting an account, " +
                        "and return to the main page?");
                mReturnToMainAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = LandingPage.intentFactory(getApplicationContext());
                        startActivity(intent); // Returns to Landing Page
                        Toast.makeText(DeleteUserActivity.this,
                                "Returning to the main page...", Toast.LENGTH_SHORT).show();
                    }
                });

                mReturnToMainAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Makes Toast message, nothing happens to the display
                        Toast.makeText(DeleteUserActivity.this,
                                "Continuing to delete account...", Toast.LENGTH_SHORT).show();
                    }
                });

                mReturnToMainAlert.show();
            }
        });

        mContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValuesFromDisplay(); // Stores the Value from the EditText Field
                // If the User does NOT exist
                if (!DeleteUserActivity.super.checkForUserInList(mInputtedText)) {
                    Toast.makeText(DeleteUserActivity.this, "No user: " + mInputtedText
                                    + " found", Toast.LENGTH_SHORT).show();
                }

                // If the String Value is Empty / Nothing was inputted into the EditText Field
                else if (mInputtedText.isEmpty()) {
                    mInputTextField.setError("The field is empty");
                }

                else {
                    // Creates Alert Dialogue
                    AlertDialog.Builder mContinueAlert = new AlertDialog.Builder(DeleteUserActivity.this);
                    mContinueAlert.setTitle("Continue onto deleting the account?");
                    mContinueAlert.setMessage("Would you like to continue onto deleting the account?");
                    mContinueAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mSavedUsername = mInputtedText; // Saves the Username
                            //mDeletedUser = mUserDAO.getUserByUsername(mSavedUsername);
                            mInstructionTextView.setText("Please Enter the Password of the Account");
                            mContinueButton.setText("Delete!");

                            mContinueButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getValuesFromDisplay(); // Gets the String value from the EditText field
                                    // If the Password didn't Match the User's password
                                    if (!DeleteUserActivity.super.validatePassword(mSavedUsername, mInputtedText)) {
                                        mInstructionTextView.setError("Invalid password. Please try again");
                                    }

                                    // If the String Value is Empty / Nothing was inputted into the EditText Field
                                    else if (mInputtedText.isEmpty()) {
                                        mInputTextField.setError("The field is empty");
                                    }

                                    else {
                                        Toast.makeText(DeleteUserActivity.this, "User: "
                                                + mSavedUsername + " has been deleted", Toast.LENGTH_SHORT).show();
                                        //mUserDAO.delete(mDeletedUser);
                                        DeleteUserActivity.super.deleteUserFromList(mInputtedText);
                                        Intent intent = LandingPage.intentFactory(getApplicationContext());
                                        startActivity(intent); // Returns to LandingPage
                                    }
                                }
                            });
                        }
                    });

                    mContinueAlert.setNegativeButton("No, Return to Main Page", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = LandingPage.intentFactory(getApplicationContext());
                            startActivity(intent); // Returns to LandingPage
                            Toast.makeText(DeleteUserActivity.this,
                                    "Returning to the main page...", Toast.LENGTH_SHORT).show();
                        }
                    });

                    mContinueAlert.show();
                }
            }
        });
    }

    private void wireUpDisplay() {
        mReturnButton = findViewById(R.id.deleteUserReturnButton);
        mContinueButton = findViewById(R.id.deleteUserContinueButton);
        mInputTextField = findViewById(R.id.deleteUserEditText);
        mInstructionTextView = findViewById(R.id.deleteUserInstructionTextView);
    }

    private void getValuesFromDisplay() {
        mInputtedText = mInputTextField.getText().toString();
    }

    public static Intent intentFactory(Context packageContext) {
        Intent intent = new Intent(packageContext, DeleteUserActivity.class);
        return intent;
    }

    /*private void getDatabase() {
        mUserDAO = AppDatabase.getInstance(this).UserDAO();
    }*/
}