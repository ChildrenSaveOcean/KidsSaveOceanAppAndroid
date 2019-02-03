package com.kidssaveocean.fatechanger.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

import com.kidssaveocean.fatechanger.database.entities.KeyValue

@Dao
interface KeyValueDao {

    @Query("SELECT * FROM KeyValue WHERE key = :key")
    fun getKeyValue(key: String): KeyValue

    @Insert
    fun insertKeyValue(keyValue: KeyValue)

    @Delete
    fun deleteKeyValue(keyValue: KeyValue)


}
