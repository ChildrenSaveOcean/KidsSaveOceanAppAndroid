package com.kidssaveocean.fatechanger.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "onboarding")
public class Onboarding {

    @PrimaryKey(autoGenerate = true)
    private int uId;

    @ColumnInfo(name = "is_onboarding_completed")
    private boolean isOnboardingCompeleted;

    public int getUId () {
        return uId;
    }

    public void setUId (int uId) {
        this.uId = uId;
    }

    public boolean isOnboardingCompeleted() {
        return isOnboardingCompeleted;
    }

    public void setOnboardingCompeleted(boolean onboardingCompeleted) {
        isOnboardingCompeleted = onboardingCompeleted;
    }

}
