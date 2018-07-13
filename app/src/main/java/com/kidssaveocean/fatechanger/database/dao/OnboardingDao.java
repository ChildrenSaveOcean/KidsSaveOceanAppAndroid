package com.kidssaveocean.fatechanger.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.kidssaveocean.fatechanger.database.entities.Onboarding;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface OnboardingDao {

    @Query("SELECT * FROM onboarding WHERE uId = 1")
    Onboarding getOnboarding ();

    @Insert (onConflict = REPLACE)
    void insertOnboaring (Onboarding onboarding);

    @Delete
    void deleteOnboarding (Onboarding onboarding);
}
