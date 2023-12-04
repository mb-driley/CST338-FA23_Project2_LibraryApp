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

//import com.example.cst338fa23_project2_libraryapp.DB.AppDatabase;
import com.example.cst338fa23_project2_libraryapp.DB.User;
//import com.example.cst338fa23_project2_libraryapp.DB.UserDAO;
import com.example.myapplication.R;

public class LandingPage extends AppCompatActivity {
    // Buttons from landing_page.xml
    private Button mAddUserButton, mDeleteUserButton, mCheckUserRentingBookButton, mViewBooksButton, mLogOutButton;
    public static User mPassedInUser; // Static User from Logging In | MainActivity
    private TextView mAdminOnlyText, mWelcomeTextView; // TextViews from landing_page.xml
    private Switch mModeSwitch; // Switch from landing_page.xml
    private boolean mNightMode; // Boolean Value for Night Mode
    SharedPreferences mSharedPreferences; // Shared Preferences for Night Mode
    SharedPreferences.Editor mEditor; // Editor to edit preferences settings for night mode
    //private static UserDAO mUserDAO; Commented Out | UserDAO obj

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page); // Sets the Content View to landing_page.xml
        //getDatabase(); Commented Out | Gets Database
        wireUpDisplay(); // Calls the wireUpDisplay method

        // Night Mode Settings for the Switch
        mSharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        mNightMode = mSharedPreferences.getBoolean("night", false);
        mWelcomeTextView.setText("Welcome " + mPassedInUser.getUsername());

        if (mNightMode) {
            mModeSwitch.setChecked(true);
        }

        // If the User is an Admin
        if (mPassedInUser.isAdmin()) {
            mViewBooksButton.setVisibility(View.INVISIBLE); // Makes Button Invisible to Admins
            mAddUserButton.setVisibility(View.VISIBLE); // Makes Button Visible to Admins
            mDeleteUserButton.setVisibility(View.VISIBLE); // Makes Button Visible to Admins
            mCheckUserRentingBookButton.setVisibility(View.VISIBLE); // Makes Button Visible to Admins
            mAdminOnlyText.setVisibility(View.VISIBLE); // Makes Button Visible to Admins
        }

        else if (!mPassedInUser.isAdmin()) {
            mAddUserButton.setVisibility(View.INVISIBLE); // Makes Button Invisible to Normal Users
            mDeleteUserButton.setVisibility(View.INVISIBLE); // Makes Button Invisible to Normal Users
            mCheckUserRentingBookButton.setVisibility(View.INVISIBLE); // Makes Button Invisible to Normal Users
            mAdminOnlyText.setVisibility(View.INVISIBLE); // Makes Button Invisible to Normal Users
        }

        // Sets an On Click Listener for the Log Out Button
        mLogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creates Alert Dialogue
                AlertDialog.Builder mLogOutAlert = new AlertDialog.Builder(LandingPage.this);
                mLogOutAlert.setTitle("Log Out");
                mLogOutAlert.setMessage("Would you like to log out of this account?");
                mLogOutAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = MainActivity.intentFactory(getApplicationContext());
                        startActivity(intent); // Returns to MainActivity/Login Page
                        Toast.makeText(LandingPage.this, "Logging out...", Toast.LENGTH_SHORT).show();
                    }
                });

                mLogOutAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Makes Toast, Nothing Happens to Display
                        Toast.makeText(LandingPage.this, "Returning to main page...", Toast.LENGTH_SHORT).show();
                    }
                });

                mLogOutAlert.show();
            }
        });

        // Sets onClickListener for the Switch | Switches Between Light & Dark Mode
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
                startActivity(intent); // Starts AddUserActivity
            }
        });

        // Sets an On Click Listener for the Delete User Button for Admins
        mDeleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = DeleteUserActivity.intentFactory(getApplicationContext());
                startActivity(intent); // Starts DeleteUserActivity
            }
        });

        // Sets an On Click Listener for the Check User Renting Book Button for Admins
        mCheckUserRentingBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Implement the intentFactory Call for the CheckUserRentingBookActivity (not implemented yet)
                Toast.makeText(LandingPage.this, "Feature not yet implemented", Toast.LENGTH_SHORT).show();
            }
        });

        // Sets an On Click Listener for the View Books Button for Normal Users
        mViewBooksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Implement the intentFactory Call for the ViewBooksActivity (not implemented yet)
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

    /*public static Intent intentFactory(Context packageContext, int passedInUserID) {
        Intent intent = new Intent(packageContext, LandingPage.class);
        mPassedInUser = mUserDAO.getUserByID(passedInUserID);
        return intent;
    }*/

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

    /*private void getDatabase() {
        mUserDAO = AppDatabase.getInstance(this).UserDAO();
    }*/

}
