package com.kidssaveocean.fatechanger.letters.master;

import com.google.common.collect.Lists;
import com.kidssaveocean.fatechanger.letters.BaseSchedulerProvider;
import com.kidssaveocean.fatechanger.letters.ImmediateSchedulerProvider;
import com.kidssaveocean.fatechanger.letters.Letter;
import com.kidssaveocean.fatechanger.letters.repository.LettersRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import io.reactivex.Flowable;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LettersMasterPresenterTest {
    private static List<Letter> LETTERS;

    @Mock
    private LettersRepository mLettersRepository;

    @Mock
    private LettersMasterContract.View mLettersView;

    private BaseSchedulerProvider mSchedulerProvider;

    private LettersMasterPresenter mLettersPresenter;


    @Before
    public void setupLettersPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Make the sure that all schedulers are immediate.
        mSchedulerProvider = new ImmediateSchedulerProvider();

        // Get a reference to the class under test
        mLettersPresenter = new LettersMasterPresenter(mLettersRepository, mLettersView, mSchedulerProvider);

        // The presenter won't update the view unless it's active.
        when(mLettersView.isActive()).thenReturn(true);

        // We subscribe the letters to 3, with one active and two completed
        LETTERS = Lists.newArrayList(new Letter("a12", "Findland", 102),
                new Letter("a12a", "Germany", 54),
                new Letter("a123", "Italy", 23));
    }

    @Test
    public void createPresenter_setsThePresenterToView() {
        // Get a reference to the class under test
        mLettersPresenter = new LettersMasterPresenter(mLettersRepository, mLettersView, mSchedulerProvider);

        // Then the presenter is set to the view
        verify(mLettersView).setPresenter(mLettersPresenter);
    }


    @Test
    public void loadAllLettersFromRepositoryAndLoadIntoView() {
        // Given an initialized LettersPresenter with initialized letters
        when(mLettersRepository.query(null /* todo: All letters*/)).thenReturn(Flowable.just(LETTERS));

        // When loading of Letters is requested
        //mLettersPresenter.setFiltering(LettersFilterType.ALL_TASKS);

        mLettersPresenter.loadLetters(true);

        // Then progress indicator is shown
        verify(mLettersView).setLoadingIndicator(true);

        verify(mLettersView).updateLettersText(Mockito.anyString());
        verify(mLettersView).updateCountriesText(Mockito.anyString());

        // Then progress indicator is hidden and all letters are shown in UI
        verify(mLettersView).setLoadingIndicator(false);
    }

    @Test
    public void errorLoadingTasks_ShowsError() {
        // Given that no tasks are available in the repository
        when(mLettersRepository.query(null /*todo*/)).thenReturn(Flowable.error(new Exception()));

        // When tasks are loaded
        mLettersPresenter.loadLetters(true);

        // Then an error message is shown
        verify(mLettersView).showLoadingLettersError();
    }

}