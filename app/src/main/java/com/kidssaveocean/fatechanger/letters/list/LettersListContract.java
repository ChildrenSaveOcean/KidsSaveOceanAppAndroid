package com.kidssaveocean.fatechanger.letters.list;

import com.kidssaveocean.fatechanger.letters.data.Letter;

import java.util.List;

public class LettersListContract {
    interface View {
        void setLetters(List<Letter> cachedLetters);
    }

    interface Presenter {
        void loadLetter();
    }
}
