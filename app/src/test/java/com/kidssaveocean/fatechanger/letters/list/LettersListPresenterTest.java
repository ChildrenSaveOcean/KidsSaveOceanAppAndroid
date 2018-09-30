package com.kidssaveocean.fatechanger.letters.list;

import com.google.common.collect.Lists;
import com.kidssaveocean.fatechanger.ImmediateSchedulerProvider;
import com.kidssaveocean.fatechanger.letters.data.Letter;
import com.kidssaveocean.fatechanger.letters.data.source.LettersRepository;
import com.kidssaveocean.fatechanger.letters.master.LettersMasterContract;
import com.kidssaveocean.fatechanger.letters.master.LettersMasterPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import io.reactivex.Flowable;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LettersListPresenterTest {
    private static List<Letter> LETTERS;

    @Mock
    private LettersRepository mLettersRepository;

    @Mock
    private LettersListContract.View mLettersView;

    private LettersListContract.Presenter mLettersPresenter;

    @Before
    public void setupLettersPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        mLettersPresenter = new LettersListPresenter(mLettersRepository, mLettersView);

        // The presenter won't update the view unless it's active.
        when(mLettersView.isActive()).thenReturn(true);

        // We subscribe the letters to 3, with one active and two completed
        LETTERS = Lists.newArrayList(new Letter("a12", "Findland", 102),
                new Letter("a12a", "Germany", 54),
                new Letter("a123", "Italy", 23));
    }

    @Test
    public void loadLetter() {
        // Given an initialized LettersPresenter with initialized letters
        when(mLettersRepository.getCachedLetters()).thenReturn(LETTERS);

        mLettersPresenter.loadLetter();

        verify(mLettersView).setLetters(LETTERS);

    }
}