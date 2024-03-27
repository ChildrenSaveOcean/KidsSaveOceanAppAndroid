package com.fatechanger.app.database.dao

import androidx.room.*

import com.fatechanger.app.database.entities.KeyValue

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
