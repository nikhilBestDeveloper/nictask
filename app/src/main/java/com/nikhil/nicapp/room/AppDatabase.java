package com.nikhil.nicapp.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.nikhil.nicapp.model.Person;

@Database(entities = {Person.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance = null;

    public abstract PersonAppDao personAppDao();

    public static AppDatabase getInstance(Context applicationContext) {
        if (instance == null) instance = Room.databaseBuilder(applicationContext,
                AppDatabase.class, "personDatabase").build();
        return instance;
    }
}