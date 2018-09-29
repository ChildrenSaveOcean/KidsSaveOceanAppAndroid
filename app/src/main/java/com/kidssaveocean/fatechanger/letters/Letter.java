package com.kidssaveocean.fatechanger.letters;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "letters_table")
public class Letter {
    @PrimaryKey
    @NonNull
    private String id;
    @NonNull
    private String country;
    @NonNull
    private int letters;

    private double latitude;
    private double longitude;

    public Letter(String id, String country, int letters) {
        this.id = id;
        this.country = country;
        this.letters = letters;
    }

    public void setCoordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public int getLetters() {
        return letters;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
