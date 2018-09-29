package com.kidssaveocean.fatechanger.letters.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.kidssaveocean.fatechanger.letters.data.Letter;
import com.kidssaveocean.fatechanger.Repository;

import java.util.List;

import io.reactivex.Flowable;

import static com.google.common.base.Preconditions.checkNotNull;


public class LettersRepository implements Repository<Letter> {
    private static final String TAG = "LettersRepository";

    @Nullable
    private static LettersRepository INSTANCE = null;

    @NonNull
    private final Repository<Letter> mLettersRemoteDataSource;

    @NonNull
    private final Repository<Letter> mLettersLocalDataSource;


    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    @VisibleForTesting
    boolean mCacheIsDirty = false;

    // Prevent direct instantiation.
    private LettersRepository(@NonNull Repository<Letter> lettersRemoteDataSource,
                              @NonNull Repository<Letter> lettersLocalDataSource) {
        mLettersRemoteDataSource = checkNotNull(lettersRemoteDataSource);
        mLettersLocalDataSource = checkNotNull(lettersLocalDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param lettersRemoteDataSource the backend data source
     * @param lettersLocalDataSource  the device storage data source
     * @return the {@link LettersRepository} instance
     */
    public static LettersRepository getInstance(@NonNull Repository<Letter> lettersRemoteDataSource,
                                                @NonNull Repository<Letter> lettersLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new LettersRepository(lettersRemoteDataSource, lettersLocalDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(Repository, Repository)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * @return list of letters from local
     */
    private Flowable<List<Letter>> getLocalLetters() {
        Flowable<List<Letter>> result = mLettersLocalDataSource.getAll();
        return result;
    }

    /**
     * Get remote Letters and save them in Local
     *
     * @return list of letters
     */
    private Flowable<List<Letter>> getAndSaveRemoteLetters() {
        return mLettersRemoteDataSource
                .getAll()
                .flatMap(letters -> {
                    mLettersLocalDataSource.removeAll();
                    return Flowable.fromIterable(letters).doOnNext(mLettersLocalDataSource::add).toList().toFlowable();
                })
                .doOnComplete(() -> mCacheIsDirty = false);
    }

    //region implementation Repository

    @Override
    public void add(Letter item) {

    }

    @Override
    public void update(Letter letter) {
        checkNotNull(letter);
        mLettersRemoteDataSource.update(letter);
        mLettersLocalDataSource.update(letter);
    }

    @Override
    public void remove(Letter letter) {
        checkNotNull(letter);
        mLettersRemoteDataSource.remove(letter);
        mLettersLocalDataSource.remove(letter);
    }

    /**
     * Gets letters from cache, local data source (SQLite) or remote data source, whichever is
     * available first.
     */
    @Override
    public Flowable<List<Letter>> getAll() {
        // Respond immediately with cache if available and not dirty
        if (!mCacheIsDirty) {
            Log.d(TAG, "getAll: local data");
            return getLocalLetters();
        } else {
            Log.d(TAG, "getAll: remote data");
            return getAndSaveRemoteLetters();
        }
    }

    @Override
    public void removeAll() {
        mLettersLocalDataSource.removeAll();
        mLettersRemoteDataSource.removeAll();
    }

    //endregion

    public void refresh() {
        mCacheIsDirty = true;
    }

}
