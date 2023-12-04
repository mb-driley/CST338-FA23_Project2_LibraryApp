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
    private EditText mInputTextField;
    private String mSavedUsername, mInputtedText;
    private Button mReturnButton, mContinueButton;
    private TextView mInstructionTextView;
    //private UserDAO mUserDAO;
    private User mDeletedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);
        //getDatabase();
        wireUpDisplay();

        mReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mReturnToMainAlert = new AlertDialog.Builder(DeleteUserActivity.this);
                mReturnToMainAlert.setTitle("Return to Main Page");
                mReturnToMainAlert.setMessage("Would you like to cancel deleting an account, " +
                        "and return to the main page?");
                mReturnToMainAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = LandingPage.intentFactory(getApplicationContext());
                        startActivity(intent);
                        Toast.makeText(DeleteUserActivity.this,
                                "Returning to the main page...", Toast.LENGTH_SHORT).show();
                    }
                });

                mReturnToMainAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
                getValuesFromDisplay();
                if (!DeleteUserActivity.super.checkForUserInList(mInputtedText)) {
                    Toast.makeText(DeleteUserActivity.this, "No user: " + mInputtedText
                                    + " found", Toast.LENGTH_SHORT).show();
                }

                else if (mInputtedText.isEmpty()) {
                    mInputTextField.setError("The field is empty");
                }

                else {
                    AlertDialog.Builder mContinueAlert = new AlertDialog.Builder(DeleteUserActivity.this);
                    mContinueAlert.setTitle("Continue onto deleting the account?");
                    mContinueAlert.setMessage("Would you like to continue onto deleting the account?");
                    mContinueAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mSavedUsername = mInputtedText;
                            //mDeletedUser = mUserDAO.getUserByUsername(mSavedUsername);
                            mInstructionTextView.setText("Please Enter the Password of the Account");
                            mContinueButton.setText("Delete!");

                            mContinueButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getValuesFromDisplay();
                                    if (!DeleteUserActivity.super.validatePassword(mSavedUsername, mInputtedText)) {
                                        mInstructionTextView.setError("Invalid password. Please try again");
                                    }

                                    else if (mInputtedText.isEmpty()) {
                                        mInputTextField.setError("The field is empty");
                                    }

                                    else {
                                        Toast.makeText(DeleteUserActivity.this, "User: "
                                                + mSavedUsername + " has been deleted", Toast.LENGTH_SHORT).show();
                                        //mUserDAO.delete(mDeletedUser);
                                        DeleteUserActivity.super.deleteUserFromList(mInputtedText);
                                        Intent intent = LandingPage.intentFactory(getApplicationContext());
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                    });

                    mContinueAlert.setNegativeButton("No, Return to Main Page", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = LandingPage.intentFactory(getApplicationContext());
                            startActivity(intent);
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