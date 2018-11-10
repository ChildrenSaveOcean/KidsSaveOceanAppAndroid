package com.kidssaveocean.fatechanger.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static com.kidssaveocean.fatechanger.database.AppDatabase.KEY_VALUE_TABLE;

@Entity(tableName = KEY_VALUE_TABLE)
public class KeyValue {

    public static final String ONBOARDING = "ONBOARDING";

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "key")
    private String key;

    @NonNull
    @ColumnInfo(name = "value")
    private String value;

    public void setKey (String key) {
        this.key = key;
    }

    public void setValue (String value) {
        this.value = value;
    }

    public String getKey () {
        return key;
    }

    public String getValue () {
        return value;
    }
}
