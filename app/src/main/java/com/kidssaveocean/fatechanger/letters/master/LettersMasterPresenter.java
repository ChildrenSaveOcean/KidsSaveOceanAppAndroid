package com.kidssaveocean.fatechanger.letters.master;

import android.support.annotation.NonNull;

import com.kidssaveocean.fatechanger.letters.BaseSchedulerProvider;
import com.kidssaveocean.fatechanger.letters.Letter;
import com.kidssaveocean.fatechanger.letters.repository.LettersRepository;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.google.common.base.Preconditions.checkNotNull;

public class LettersMasterPresenter implements LettersMasterContract.Presenter {
    private final LettersRepository mRepository;
    private final LettersMasterContract.View mView;
    private final BaseSchedulerProvider mSchedulerProvider;

    private boolean mFirstLoad = true;

    @NonNull
    private CompositeDisposable mCompositeDisposable;

    public LettersMasterPresenter(LettersRepository repository, LettersMasterContract.View view
            , BaseSchedulerProvider schedulerProvider) {
        mRepository = checkNotNull(repository, "lettersRepository cannot be null");
        mView = checkNotNull(view, "lettersView cannot be null!");
        mSchedulerProvider = checkNotNull(schedulerProvider, "schedulerProvider cannot be null");
        mCompositeDisposable = new CompositeDisposable();
        this.mView.setPresenter(this);
    }

    // region implementation LettersMasterContract.Presenter

    @Override
    public void subscribe() {
        loadLetters(true);
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void loadLetters(boolean forceUpdate) {
        // Simplification for sample: a network reload will be forced on first load.
        loadLetters(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;

    }

    // endregion

    /**
     * @param forceUpdate   Pass in true to refresh the data in the {@link LettersRepository}
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private void loadLetters(final boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            mView.setLoadingIndicator(true);
        }
        if (forceUpdate) {
            mRepository.refresh();
        }

        mCompositeDisposable.clear();
        Disposable disposable = mRepository
                .query(null) // Todo:
                .flatMap(Flowable::fromIterable)
                .toList()
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(
                        // onNext
                        letters -> {
                            processLetters(letters);
                            mView.setLoadingIndicator(false);
                        },
                        // onError
                        throwable -> mView.showLoadingLettersError());

        mCompositeDisposable.add(disposable);
    }

    private void processLetters(@NonNull List<Letter> letters) {
        String nLetters = "0";
        String nCountries = "0";
        if (letters.isEmpty()) {
            // Show a message indicating there are no letters for that filter type.
            mView.noLetters();
        } else {
            int i = letters.stream().mapToInt(Letter::getLetters).sum();
            nLetters = String.valueOf(i);
            nCountries = String.valueOf(letters.size());
            // Show the list of letters
            mView.showLetters(letters);
        }

        mView.updateCountriesText(nCountries);
        mView.updateLettersText(nLetters);
    }

}
