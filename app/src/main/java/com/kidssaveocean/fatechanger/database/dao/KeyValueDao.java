package com.kidssaveocean.fatechanger.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.kidssaveocean.fatechanger.database.entities.KeyValue;

@Dao
public interface KeyValueDao {

    @Query("SELECT * FROM KeyValue WHERE key = :key")
    KeyValue getKeyValue (String key);

    @Insert
    void insertKeyValue (KeyValue keyValue);

    @Delete
    void deleteKeyValue (KeyValue keyValue);


}
