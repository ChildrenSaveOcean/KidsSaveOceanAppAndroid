package com.kidssaveocean.fatechanger.letters.map;

import com.kidssaveocean.fatechanger.letters.data.Letter;

import java.util.List;

public class MapContract {
    interface View {
        void setLetters(List<Letter> cachedLetters);
        boolean isActive();
    }

    interface Presenter {
        void loadLetter();
    }
}
