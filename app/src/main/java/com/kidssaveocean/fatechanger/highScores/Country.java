package com.kidssaveocean.fatechanger.highScores;

import android.support.annotation.NonNull;

public class Country implements Comparable<Country> {
    public String name;
    public int numOfLetters;
    public int rank;

    public Country (String name, int numOfLetters) {
        this.name = name;
        this.numOfLetters = numOfLetters;
    }

    @Override
    public int compareTo(@NonNull Country otherCountry) {
        return otherCountry.numOfLetters - numOfLetters;
    }
}
