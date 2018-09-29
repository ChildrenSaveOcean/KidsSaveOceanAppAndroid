package com.kidssaveocean.fatechanger.letters;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface LetterDao {
    @Insert
    void insert(Letter letter);

    @Query("DELETE FROM letters_table")
    void deleteAll();

    @Query("SELECT * from letters_table ORDER BY letters ASC")
    Flowable<List<Letter>> getAllLetters();
}
