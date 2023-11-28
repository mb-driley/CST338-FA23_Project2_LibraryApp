package com.example.cst338fa23_project2_libraryapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LandingPage extends AppCompatActivity {
    private Button mAddUserButton, mDeleteUserButton, mCheckUserRentingBookButton, mViewBooksButton;
    public static User mPassedInUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);
        wireUpDisplay();

        if (mPassedInUser.isAdmin()) {
            mAddUserButton.setVisibility(View.VISIBLE);
            mDeleteUserButton.setVisibility(View.VISIBLE);
            mCheckUserRentingBookButton.setVisibility(View.VISIBLE);
        }

        else if (!mPassedInUser.isAdmin()) {
            mAddUserButton.setVisibility(View.INVISIBLE);
            mDeleteUserButton.setVisibility(View.INVISIBLE);
            mCheckUserRentingBookButton.setVisibility(View.INVISIBLE);
        }
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
    }

}
