package com.example.cst338fa23_project2_libraryapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LandingPage extends AppCompatActivity {
    private Button mAddUserButton, mDeleteUserButton, mCheckUserRentingBookButton, mViewBooksButton, mLogOutButton;
    public static User mPassedInUser;
    private TextView mAdminOnlyText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);
        wireUpDisplay();

        if (mPassedInUser.isAdmin()) {
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

        mLogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        // Sets an On Click Listener for the Add User Button for Admins
        mAddUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LandingPage.this, "Feature not yet implemented", Toast.LENGTH_SHORT).show();
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
    }

}
