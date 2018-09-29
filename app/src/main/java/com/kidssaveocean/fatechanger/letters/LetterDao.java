package com.kidssaveocean.fatechanger.letters;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface LetterDao {
    @Insert
    void insertLetter(Letter letter);

    @Query("DELETE FROM letters")
    void deleteAllLetters();

    @Query("SELECT * FROM letters")
    Flowable<List<Letter>> getAllLetters();

    @Delete
    void deleteLetter(Letter letter);
}
