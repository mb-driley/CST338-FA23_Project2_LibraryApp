package com.example.cst338fa23_project2_libraryapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DeleteUserActivity extends MainActivity {
    private EditText mUsernameField, mPasswordField;
    private String mUsername, mPassword;
    private Button mReturnButton, mContinueButton;
    private TextView mInstructionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);

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
                                "Continuing to create account...", Toast.LENGTH_SHORT).show();
                    }
                });

                mReturnToMainAlert.show();
            }
        });
    }

    private void wireUpDisplay() {
        mReturnButton = findViewById(R.id.deleteUserReturnButton);
        mContinueButton = findViewById(R.id.deleteUserContinueButton);
        mUsernameField = findViewById(R.id.deleteUserUsernameEditText);
        mPasswordField = findViewById(R.id.deleteUserPasswordEditText);
        mInstructionTextView = findViewById(R.id.deleteUserInstructionTextView);
    }

    private void getValuesFromDisplay() {
        mUsername = mUsernameField.getText().toString();
        mPassword = mPasswordField.getText().toString();
    }
}