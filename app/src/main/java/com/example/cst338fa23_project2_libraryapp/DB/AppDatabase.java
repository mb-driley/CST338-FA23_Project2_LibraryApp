package com.example.cst338fa23_project2_libraryapp.DB;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "Users.db";
    private static final String USER_TABLE = "user_table";
    private static volatile AppDatabase instance;
    public abstract UserDAO UserDAO();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                    DB_NAME).fallbackToDestructiveMigration().build();
        }

        return instance;
    }

    public static void clearDatabase(AppDatabase appDatabase) {
        new ClearDatabaseTask(appDatabase).execute();
    }

    private static class ClearDatabaseTask extends AsyncTask<Void, Void, Void> {
        private AppDatabase appDatabase;

        ClearDatabaseTask(AppDatabase appDatabase) {
            this.appDatabase = appDatabase;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            appDatabase.clearAllTables();
            return null;
        }
    }
}
