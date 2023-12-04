package com.example.cst338fa23_project2_libraryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cst338fa23_project2_libraryapp.DB.AppDatabase;
import com.example.cst338fa23_project2_libraryapp.DB.User;
import com.example.cst338fa23_project2_libraryapp.DB.UserDAO;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText mUsernameField, mPasswordField;
    private Button mLoginButton;
    private String mUsername, mPassword;
    List<User> mUserList = new ArrayList<>();
    private UserDAO mUserDAO;
    User mUser;
    User mDefaultAdminUser = new User(1, "testuser1", "testuser1", false);
    User mDefaultTestUser = new User(2, "admin2", "admin2", true);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase mAppDatabase = AppDatabase.getInstance(this);
        mUserDAO = mAppDatabase.UserDAO();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mUserDAO.insert(mDefaultTestUser);
                mUserDAO.insert(mDefaultAdminUser);
                return null;
            }

            protected void onPostExecute(Void result) {
                //AppDatabase.clearDatabase(mAppDatabase);
                getDatabase();
                wireUpDisplayDAO();
            }
        }.execute();


        /*mUserList.add(mDefaultTestUser);
        mUserList.add(mDefaultAdminUser);
        wireUpDisplay();*/
    }

    private void wireUpDisplay() {
        mUsernameField = findViewById(R.id.editTextLoginUserName);
        mPasswordField = findViewById(R.id.editTextLoginPassword);
        mLoginButton = findViewById(R.id.buttonLogin);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValuesFromDisplay();
                if (checkForUserInDatabase()) {
                    if (!validatePassword()) {
                        mPasswordField.setError("Invalid Password. Please Try Again");
                    }
                }
            }
        });
    }

    private void wireUpDisplayDAO() {
        mUsernameField = findViewById(R.id.editTextLoginUserName);
        mPasswordField = findViewById(R.id.editTextLoginPassword);
        mLoginButton = findViewById(R.id.buttonLogin);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValuesFromDisplay();
                mUser = checkForUserInList();
                if (mUser != null) {
                    if (validatePassword()) {
                        Intent intent = LandingPage.intentFactory(getApplicationContext(), mUser);
                        startActivity(intent);
                    } else {
                        mPasswordField.setError("Invalid Password. Please Try Again");
                    }
                } else {
                    if (mUser.isAdmin()) {
                        Intent intent = MainActivity.intentFactory(getApplicationContext(),
                                mUser.getUserID());
                        startActivity(intent);
                    } else {
                        Intent intent = MainActivity.intentFactory(getApplicationContext(),
                                mUser.getUserID());
                        startActivity(intent);
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

    public User checkForUserInList() {
        User mUser = null;
        for (User user: mUserList) {
            if (mUsername.equals(user.getUsername())) {
                mUser = user;
            }
        }

        if (mUser == null) {
            Toast.makeText(MainActivity.this, "No user: " + mUsername + " found",
                    Toast.LENGTH_SHORT).show();
            return null;
        }

        return mUser;
    }

    public boolean checkForUserInList(String username) {
        boolean mAlreadyUsed = false;
        for (User user: mUserList) {
            if (username.equals(user.getUsername())) {
                mAlreadyUsed = true;
            }
        }

        return mAlreadyUsed;
    }

    private boolean checkForUserInDatabase() {
        mUser = mUserDAO.getUserByUsername(mUsername);
        if (mUser == null) {
            mUsernameField.setError("No user: " + mUsername + " found");
            return false;
        }

        return true;
    }

    public int getUserListSize() {return mUserList.size();}

    public boolean validatePassword() {
        return mUser.getPassword().equals(mPassword);
    }

    public boolean validatePassword(String username, String password) {
        boolean mCorrectPassword = false;
        for (User user: mUserList) {
            if (username.equals(user.getUsername())) {
                if (user.getPassword().equals(password)) {
                    mCorrectPassword = true;
                }
            }
        }

        return mCorrectPassword;
    }

    public void addUserToList(User user) {mUserList.add(user);}

    public void deleteUserFromList(String password) {
        for (User user: mUserList) {
            if (password.equals(user.getPassword())) {
                mUserList.remove(user);
            }
        }
    }

    private void getDatabase() {
        mUserDAO = AppDatabase.getInstance(this).UserDAO();
    }
}