package com.example.cst338fa23_project2_libraryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddUserActivity extends AppCompatActivity {

    private Button mReturnButton, mCreateAccountButton;
    private EditText mUsernameField, mPasswordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        wireUpDisplay();


    }

    private void wireUpDisplay() {
        mReturnButton = findViewById(R.id.addUserReturnButton);
        mCreateAccountButton = findViewById(R.id.addUserCreateButton);
        mUsernameField = findViewById(R.id.addUserUsernameEditText);
        mPasswordField = findViewById(R.id.addUserPasswordEditText);
    }
}