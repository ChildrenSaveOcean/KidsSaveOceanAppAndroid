package com.kidssaveocean.fatechanger.database.dao

import androidx.room.*

import com.kidssaveocean.fatechanger.database.entities.KeyValue

@Dao
interface KeyValueDao {

    @Query("SELECT * FROM KeyValue WHERE `key` = :key")
    fun getKeyValue(key: String): KeyValue?

    @Insert
    fun insertKeyValue(keyValue: KeyValue)

    @Delete
    fun deleteKeyValue(keyValue: KeyValue)

    @Update
    fun updateKeyValue(keyValue: KeyValue)
}
