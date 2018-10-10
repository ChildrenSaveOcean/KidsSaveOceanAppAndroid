package com.kidssaveocean.fatechanger.letters.master;

import android.support.annotation.NonNull;
import android.util.Log;

import com.kidssaveocean.fatechanger.BaseSchedulerProvider;
import com.kidssaveocean.fatechanger.BuildConfig;
import com.kidssaveocean.fatechanger.letters.data.Letter;
import com.kidssaveocean.fatechanger.letters.data.source.LettersRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.google.common.base.Preconditions.checkNotNull;

public class LettersMasterPresenter implements LettersMasterContract.Presenter {

    private static final String TAG = "LettersMasterPresenter";

    private final LettersRepository mRepository;
    private final LettersMasterContract.View mView;
    private final BaseSchedulerProvider mSchedulerProvider;

    private boolean mFirstLoad = true;

    // container of disposables,
    @NonNull
    private CompositeDisposable mCompositeDisposable;

    //region constructor
    public LettersMasterPresenter(LettersRepository repository, LettersMasterContract.View view
            , BaseSchedulerProvider schedulerProvider) {
        mRepository = checkNotNull(repository, "lettersRepository cannot be null");
        mView = checkNotNull(view, "lettersView cannot be null!");
        mSchedulerProvider = checkNotNull(schedulerProvider, "schedulerProvider cannot be null");
        mCompositeDisposable = new CompositeDisposable();
        this.mView.setPresenter(this);
    }
    //endregion

    // region implementation LettersMasterContract.Presenter

    @Override
    public void subscribe() {
        loadLetters(false);
    }

    @Override
    public void unsubscribe() {
        Log.d(TAG, "unsubscribe: ");
        mCompositeDisposable.clear();
    }

    @Override
    public void loadLetters(boolean forceUpdate) {
        // Simplification for sample: a network reload will be forced on first load.
        loadLetters(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    // endregion

    //region private methods

    /**
     * @param forceUpdate   Pass in true to refresh the data in the {@link LettersRepository}
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private void loadLetters(final boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            mView.setLoadingIndicator(true);
        }
        if (forceUpdate) {
            Log.d(TAG, "loadLetters: forceUpload");
            mRepository.refresh();
        }

        int seconds = 0;
        if (BuildConfig.DEBUG)
            seconds = 3;

        mCompositeDisposable.clear();
        Disposable disposable = mRepository.getAll()
                .delay(seconds, TimeUnit.SECONDS)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(
                        //onNext
                        this::processLetters,
                        //onError
                        throwable -> {
                            Log.e(TAG, "error: " + throwable.getMessage());
                            mView.setLoadingIndicator(false);
                            mView.showLoadingLettersError();
                        },
                        //onCompletion
                        () -> mView.setLoadingIndicator(false));


        mCompositeDisposable.add(disposable);
    }

    private void processLetters(@NonNull List<Letter> letters) {
        String nLetters = "0";
        String nCountries = "0";
        if (letters.isEmpty()) {
            // Show a message indicating there are no letters for that filter type.
            mView.noLetters();
        } else {
            int i = getNLetters(letters);
            nLetters = String.valueOf(i);
            nCountries = String.valueOf(letters.size());
        }

        mView.updateCountriesText(nCountries);
        mView.updateLettersText(nLetters);
        mView.setUpPagerAdapter();
    }

    private int getNLetters(List<Letter> letters) {
        int i = 0;
        for (Letter letter : letters) {
            i += letter.getLetters();
        }
        return i;
    }
    //endregion

}
