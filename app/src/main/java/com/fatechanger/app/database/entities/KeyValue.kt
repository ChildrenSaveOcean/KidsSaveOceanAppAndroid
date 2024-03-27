package com.fatechanger.app.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

import com.fatechanger.app.database.AppDatabase.Companion.KEY_VALUE_TABLE

@Entity(tableName = KEY_VALUE_TABLE)
data class KeyValue ( @PrimaryKey @ColumnInfo(name = "key") var key: String,
                      @ColumnInfo(name = "value") var value: String) {

    companion object {
        const val ONBOARDING = "ONBOARDING"
        const val LAST_CURRENT_STEP = "LAST_CURRENT_STEP"
    }

}
