package com.kidssaveocean.fatechanger.letters.master;

import com.kidssaveocean.fatechanger.letters.BasePresenter;
import com.kidssaveocean.fatechanger.letters.Letter;

import java.util.List;

public class LettersMasterContract {
    public interface View {

        void setPresenter(LettersMasterPresenter lettersMasterPresenter);

        boolean isActive();

        void setLoadingIndicator(boolean active);

        void showLetters(List<Letter> letters);

        void noLetters();

        void showLoadingLettersError();

        void updateLettersText(String s);

        void updateCountriesText(String s);
    }

    interface Presenter extends BasePresenter {

        void loadLetters(boolean forceUpdate);
    }
}
