package com.kidssaveocean.fatechanger.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.kidssaveocean.fatechanger.database.dao.KeyValueDao;
import com.kidssaveocean.fatechanger.database.entities.KeyValue;

@Database(entities = {KeyValue.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public static final String KEY_VALUE_TABLE = "KeyValue";

    private static AppDatabase INSTANCE;
    private static String DB_NAME = "FateChanger";

    public abstract KeyValueDao keyValueDao();

    public static AppDatabase getAppDatabase (Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                                   .build();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance () {
        INSTANCE = null;
    }
}
