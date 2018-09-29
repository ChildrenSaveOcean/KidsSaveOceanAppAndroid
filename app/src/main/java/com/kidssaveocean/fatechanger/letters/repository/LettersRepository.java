package com.kidssaveocean.fatechanger.letters.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.kidssaveocean.fatechanger.letters.Letter;
import com.kidssaveocean.fatechanger.letters.Repository;
import com.kidssaveocean.fatechanger.letters.Specification;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;

import static com.google.common.base.Preconditions.checkNotNull;


public class LettersRepository implements Repository<Letter> {
    @Nullable
    private static LettersRepository INSTANCE = null;

    @NonNull
    private final Repository<Letter> mLettersRemoteDataSource;

    @NonNull
    private final Repository<Letter> mLettersLocalDataSource;


    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    @VisibleForTesting
    @Nullable
    Map<String, Letter> mCachedLetters;

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


    private Flowable<List<Letter>> getAndCacheLocalLetters() {
        return mLettersLocalDataSource.query(null) //Todo
                .flatMap(letters -> Flowable.fromIterable(letters)
                        .doOnNext(letter -> mCachedLetters.put(letter.getId(), letter))
                        .toList()
                        .toFlowable());
    }

    private Flowable<List<Letter>> getAndSaveRemoteLetters() {
        return mLettersRemoteDataSource
                .query(null)
                .flatMap(letters -> Flowable.fromIterable(letters).doOnNext(letter -> {
                    mLettersLocalDataSource.add(letter);
                    mCachedLetters.put(letter.getId(), letter);
                }).toList().toFlowable())
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

        // Do in memory cache update to keep the app UI up to date
        if (mCachedLetters == null) {
            mCachedLetters = new LinkedHashMap<>();
        }
        mCachedLetters.put(letter.getId(), letter);
    }

    @Override
    public void remove(Letter item) {

    }

    /**
     * Gets letters from cache, local data source (SQLite) or remote data source, whichever is
     * available first.
     */
    @Override
    public Flowable<List<Letter>> query(Specification specification) {
        // Todo
        // Respond immediately with cache if available and not dirty
        if (mCachedLetters != null && !mCacheIsDirty) {
            return Flowable.fromIterable(mCachedLetters.values()).toList().toFlowable();
        } else if (mCachedLetters == null) {
            mCachedLetters = new LinkedHashMap<>();
        }

        Flowable<List<Letter>> remoteLetters = getAndSaveRemoteLetters();

        if (mCacheIsDirty) {
            return remoteLetters;
        } else {
            // Query the local storage if available. If not, query the network.
            Flowable<List<Letter>> localLetters = getAndCacheLocalLetters();
            return Flowable.concat(localLetters, remoteLetters)
                    .filter(letters -> !letters.isEmpty())
                    .firstOrError()
                    .toFlowable();
        }
    }



    //endregion


    public void refresh() {
        mCacheIsDirty = true;
    }
}
