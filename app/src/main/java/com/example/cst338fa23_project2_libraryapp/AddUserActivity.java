package com.example.cst338fa23_project2_libraryapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddUserActivity extends AppCompatActivity {

    private Button mReturnButton, mCreateAccountButton;
    private EditText mUsernameField, mPasswordField;

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
                mReturnToMainAlert.setMessage("Would you like to cancel creating an account, and return to the main page?");
                mReturnToMainAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = LandingPage.intentFactory(getApplicationContext());
                        startActivity(intent);
                        Toast.makeText(AddUserActivity.this, "Returning to the main page...", Toast.LENGTH_SHORT).show();
                    }
                });

                mReturnToMainAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AddUserActivity.this, "Continuing to create account...", Toast.LENGTH_SHORT).show();
                    }
                });

                mReturnToMainAlert.show();
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
}