package com.example.cst338fa23_project2_libraryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText mUsernameField, mPasswordField;
    private Button mButton;
    private String mUsername, mPassword;
    List<User> mUserList = new ArrayList<>();
    User mDefaultAdminUser = new User(1, "admin", "admin", true);
    User mDefaultTestUser = new User(2, "tester", "tester", false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUserList.add(mDefaultTestUser);
        mUserList.add(mDefaultAdminUser);
        wireUpDisplay();
    }

    private void wireUpDisplay() {
        mUsernameField = findViewById(R.id.editTextLoginUserName);
        mPasswordField = findViewById(R.id.editTextLoginPassword);
        mButton = findViewById(R.id.buttonLogin);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValuesFromDisplay();

                for (int i = 0; i < mUserList.size(); i++) {
                    if (mUsername.equals(mUserList.get(i).getUsername())) {
                        if (mPassword.equals(mUserList.get(i).getPassword())) {
                            if (mUserList.get(i).isAdmin()) {
                                Toast.makeText(MainActivity.this, "Correct Password & is Admin", Toast.LENGTH_SHORT).show();
                                Intent intent = LandingPage.intentFactory(getApplicationContext(), mUserList.get(i));
                                startActivity(intent);
                            }

                            else if (!mUserList.get(i).isAdmin()) {
                                Toast.makeText(MainActivity.this, "Correct password, but not admin", Toast.LENGTH_SHORT).show();
                                Intent intent = LandingPage.intentFactory(getApplicationContext(), mUserList.get(i));
                                startActivity(intent);
                            }
                        }

                        else {
                            Toast.makeText(MainActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                        }
                    }

                    else {
                        Toast.makeText(MainActivity.this, "No user: " + mUsername + " found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    private void getValuesFromDisplay() {
        mUsername = mUsernameField.getText().toString();
        mPassword = mPasswordField.getText().toString();
    }

    public static Intent intentFactory(Context packageContext) {
        Intent intent = new Intent(packageContext, MainActivity.class);
        return intent;
    }
}