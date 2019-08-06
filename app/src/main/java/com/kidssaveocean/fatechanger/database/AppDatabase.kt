package com.kidssaveocean.fatechanger.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

import com.kidssaveocean.fatechanger.database.dao.KeyValueDao
import com.kidssaveocean.fatechanger.database.entities.KeyValue

@Database(entities = [KeyValue::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun keyValueDao(): KeyValueDao

    companion object {
        const val KEY_VALUE_TABLE = "KeyValue"
        private const val DB_NAME = "FateChanger"

        private var INSTANCE: AppDatabase? = null

        fun getAppDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME)
                                       .build()
                    }
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
