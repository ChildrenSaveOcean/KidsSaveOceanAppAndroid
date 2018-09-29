package com.kidssaveocean.fatechanger.letters.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.kidssaveocean.fatechanger.letters.data.Letter;

@Database(entities = {Letter.class}, version = 1)
public abstract class LetterRoomDatabase extends RoomDatabase {


    public abstract LetterDao letterDao();

    private static volatile LetterRoomDatabase INSTANCE;

    public static LetterRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LetterRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LetterRoomDatabase.class, "letters_db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
