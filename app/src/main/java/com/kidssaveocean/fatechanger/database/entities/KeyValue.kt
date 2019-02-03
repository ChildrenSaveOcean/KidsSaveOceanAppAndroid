package com.kidssaveocean.fatechanger.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

import com.kidssaveocean.fatechanger.database.AppDatabase.Companion.KEY_VALUE_TABLE

@Entity(tableName = KEY_VALUE_TABLE)
data class KeyValue ( @PrimaryKey @ColumnInfo(name = "key") var key: String,
                      @ColumnInfo(name = "value") var value: String) {

    companion object {
        const val ONBOARDING = "ONBOARDING"
    }

}
