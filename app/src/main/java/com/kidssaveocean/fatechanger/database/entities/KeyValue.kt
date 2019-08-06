package com.kidssaveocean.fatechanger.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

import com.kidssaveocean.fatechanger.database.AppDatabase.Companion.KEY_VALUE_TABLE

@Entity(tableName = KEY_VALUE_TABLE)
data class KeyValue ( @PrimaryKey @ColumnInfo(name = "key") var key: String,
                      @ColumnInfo(name = "value") var value: String) {

    companion object {
        const val ONBOARDING = "ONBOARDING"
    }

}
