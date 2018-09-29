package com.kidssaveocean.fatechanger.letters;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "letters")
public class Letter {
    @PrimaryKey
    @NonNull
    private String id;
    @NonNull
    private String country;
    @NonNull
    private int letters;


    public Letter(@NonNull String id, @NonNull String country, int letters) {
        this.id = id;
        this.country = country;
        this.letters = letters;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getCountry() {
        return country;
    }

    public void setCountry(@NonNull String country) {
        this.country = country;
    }

    public int getLetters() {
        return letters;
    }

    public void setLetters(int letters) {
        this.letters = letters;
    }
}
