package com.kidssaveocean.fatechanger.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

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
