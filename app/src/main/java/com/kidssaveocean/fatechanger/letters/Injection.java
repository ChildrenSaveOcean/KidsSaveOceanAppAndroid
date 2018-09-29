package com.kidssaveocean.fatechanger.letters;

import android.content.Context;
import android.support.annotation.NonNull;

import com.kidssaveocean.fatechanger.BaseSchedulerProvider;
import com.kidssaveocean.fatechanger.SchedulerProvider;
import com.kidssaveocean.fatechanger.letters.data.source.local.LetterRoomDatabase;
import com.kidssaveocean.fatechanger.Repository;
import com.kidssaveocean.fatechanger.letters.data.source.remote.FakeLettersRemoteDataSource;
import com.kidssaveocean.fatechanger.letters.data.source.local.LettersLocalDataSource;
import com.kidssaveocean.fatechanger.letters.data.source.LettersRepository;

/**
 * Enables injection of mock implementations for
 * {@link Repository} at compile time. This is useful for testing, since it allows us to use
 * a fake instance of the class to isolate the dependencies and run a test hermetically.
 */
public class Injection {

    public static LettersRepository provideLettersRepository(@NonNull Context context) {
        LetterRoomDatabase database = LetterRoomDatabase.getDatabase(context);
        return LettersRepository.getInstance(FakeLettersRemoteDataSource.getInstance(),
                LettersLocalDataSource.getInstance(database.letterDao()));
    }

    public static BaseSchedulerProvider provideSchedulerProvider() {
        return SchedulerProvider.getInstance();
    }
}