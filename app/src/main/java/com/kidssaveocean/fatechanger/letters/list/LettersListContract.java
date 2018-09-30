package com.kidssaveocean.fatechanger.letters.list;

import com.kidssaveocean.fatechanger.letters.data.Letter;

import java.util.List;

class LettersListContract {
    interface View {
        void setLetters(List<Letter> cachedLetters);
        boolean isActive();
    }

    interface Presenter {
        void loadLetter();
    }
}
