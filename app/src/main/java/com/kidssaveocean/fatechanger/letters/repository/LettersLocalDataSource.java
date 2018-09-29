package com.kidssaveocean.fatechanger.letters.repository;

import android.app.Application;

import com.kidssaveocean.fatechanger.letters.Letter;
import com.kidssaveocean.fatechanger.letters.LetterDao;
import com.kidssaveocean.fatechanger.letters.LetterRoomDatabase;
import com.kidssaveocean.fatechanger.letters.Repository;
import com.kidssaveocean.fatechanger.letters.Specification;

import java.util.List;

import io.reactivex.Flowable;

public class LettersLocalDataSource implements Repository<Letter> {
    LetterDao letterDao;

    //region singleton implementation
    private static LettersLocalDataSource INSTANCE;

    public static LettersLocalDataSource getInstance(Application application) {
        if (INSTANCE == null) {
            INSTANCE = new LettersLocalDataSource(application);
        }
        return INSTANCE;
    }

    private LettersLocalDataSource(Application application) {
        LetterRoomDatabase db = LetterRoomDatabase.getDatabase(application);
        letterDao = db.letterDao();
    }
    //endregion

    @Override
    public void add(Letter item) {
        letterDao.insert(item);
    }

    @Override
    public void update(Letter item) {
        // Todo
    }

    @Override
    public void remove(Letter item) {
        // Todo
    }

    @Override
    public Flowable<List<Letter>> query(Specification specification) {
        //Todo
        return letterDao.getAllLetters();
    }

}
