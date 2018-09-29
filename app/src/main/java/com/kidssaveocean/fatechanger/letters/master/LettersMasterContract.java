package com.kidssaveocean.fatechanger.letters.master;

import com.kidssaveocean.fatechanger.BasePresenter;
import com.kidssaveocean.fatechanger.letters.data.Letter;

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

        void setUpPagerAdapter();
    }

    interface Presenter extends BasePresenter {
        void loadLetters(boolean forceUpdate);
    }

}
