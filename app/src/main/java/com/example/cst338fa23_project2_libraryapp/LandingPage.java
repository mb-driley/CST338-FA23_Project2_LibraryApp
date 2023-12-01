package com.example.cst338fa23_project2_libraryapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class LandingPage extends AppCompatActivity {
    private Button mAddUserButton, mDeleteUserButton, mCheckUserRentingBookButton, mViewBooksButton, mLogOutButton;
    public static User mPassedInUser;
    private TextView mAdminOnlyText, mWelcomeTextView;
    private Switch mModeSwitch;
    private boolean mNightMode;
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);
        wireUpDisplay();
        mSharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        mNightMode = mSharedPreferences.getBoolean("night", false);
        mWelcomeTextView.setText("Welcome " + mPassedInUser.getUsername());

        if (mNightMode) {
            mModeSwitch.setChecked(true);
        }

        if (mPassedInUser.isAdmin()) {
            mViewBooksButton.setVisibility(View.INVISIBLE);
            mAddUserButton.setVisibility(View.VISIBLE);
            mDeleteUserButton.setVisibility(View.VISIBLE);
            mCheckUserRentingBookButton.setVisibility(View.VISIBLE);
            mAdminOnlyText.setVisibility(View.VISIBLE);
        }

        else if (!mPassedInUser.isAdmin()) {
            mAddUserButton.setVisibility(View.INVISIBLE);
            mDeleteUserButton.setVisibility(View.INVISIBLE);
            mCheckUserRentingBookButton.setVisibility(View.INVISIBLE);
            mAdminOnlyText.setVisibility(View.INVISIBLE);
        }

        // Sets an On Click Listener for the Log Out Button
        mLogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mLogOutAlert = new AlertDialog.Builder(LandingPage.this);
                mLogOutAlert.setTitle("Log Out");
                mLogOutAlert.setMessage("Would you like to log out of this account?");
                mLogOutAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = MainActivity.intentFactory(getApplicationContext());
                        startActivity(intent);
                        Toast.makeText(LandingPage.this, "Logging out...", Toast.LENGTH_SHORT).show();
                    }
                });

                mLogOutAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(LandingPage.this, "Returning to main page...", Toast.LENGTH_SHORT).show();
                    }
                });

                mLogOutAlert.show();
            }
        });

        mModeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNightMode) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    mEditor = mSharedPreferences.edit();
                    mEditor.putBoolean("night", false);
                }

                else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    mEditor = mSharedPreferences.edit();
                    mEditor.putBoolean("night", true);
                }

                mEditor.apply();
            }
        });

        // Sets an On Click Listener for the Add User Button for Admins
        mAddUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AddUserActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        // Sets an On Click Listener for the Delete User Button for Admins
        mDeleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LandingPage.this, "Feature not yet implemented", Toast.LENGTH_SHORT).show();
            }
        });

        // Sets an On Click Listener for the Check User Renting Book Button for Admins
        mCheckUserRentingBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LandingPage.this, "Feature not yet implemented", Toast.LENGTH_SHORT).show();
            }
        });

        // Sets an On Click Listener for the View Books Button for Normal Users
        mViewBooksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LandingPage.this, "Feature not yet implemented", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static Intent intentFactory(Context packageContext) {
        Intent intent = new Intent(packageContext, LandingPage.class);
        return intent;
    }

    public static Intent intentFactory(Context packageContext, User user) {
        Intent intent = new Intent(packageContext, LandingPage.class);
        mPassedInUser = user;
        return intent;
    }

    private void wireUpDisplay() {
        mAddUserButton = findViewById(R.id.buttonAddUser);
        mDeleteUserButton = findViewById(R.id.buttonDeleteUser);
        mCheckUserRentingBookButton = findViewById(R.id.buttonCheckUserRentingBook);
        mViewBooksButton = findViewById(R.id.buttonViewBooks);
        mLogOutButton = findViewById(R.id.landingPageLogOutButton);
        mAdminOnlyText = findViewById(R.id.landingPageAdminOnlyTextView);
        mWelcomeTextView = findViewById(R.id.landingPageWelcomeTextView);
        mModeSwitch = findViewById(R.id.landingPageModeSwitch);
    }

}
