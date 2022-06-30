package com.example.mobilebanking.database;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mobilebanking.database.dao.TranzactionDao;
import com.example.mobilebanking.database.dao.UserDao;
import com.example.mobilebanking.database.models.Transaction;
import com.example.mobilebanking.database.models.User;

@Database(entities={User.class,Transaction.class},exportSchema =false,version=1)
public abstract class DatabaseManager extends RoomDatabase {

    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(
            Context context) {
        if (databaseManager == null) {
            synchronized (DatabaseManager.class) {
                if (databaseManager == null) {
                    databaseManager = Room
                            .databaseBuilder(context,
                                    DatabaseManager.class,
                                    "pocketBank3")
                            .fallbackToDestructiveMigration()
                            .build();
                    return databaseManager;
                }
            }
        }
        return databaseManager;
    }

public abstract UserDao getUserDao();

public abstract TranzactionDao getTranzactionDao();
}
