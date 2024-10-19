package com.kidssavetheocean.fatechanger.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.kidssavetheocean.fatechanger.dashboard.DashboardSteps
import com.kidssavetheocean.fatechanger.database.dao.DashboardStepDao
import com.kidssavetheocean.fatechanger.database.dao.KeyValueDao
import com.kidssavetheocean.fatechanger.database.entities.DashboardStep
import com.kidssavetheocean.fatechanger.database.entities.KeyValue

@Database(entities = [KeyValue::class, DashboardStep::class], version = 1, exportSchema = false)
@TypeConverters(StepConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun keyValueDao(): KeyValueDao
    abstract fun dashboardStepDao(): DashboardStepDao

    companion object {
        const val KEY_VALUE_TABLE = "KeyValue"
        const val DASHBOARD_STEPS_TABLE = "DashboardSteps"
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
    }
}

class StepConverter {
    @TypeConverter
    fun toStep(key: String): DashboardSteps {
        return when(key) {
            DashboardSteps.RESEARCH.name -> DashboardSteps.RESEARCH
            DashboardSteps.WRITE_LETTER.name -> DashboardSteps.WRITE_LETTER
            DashboardSteps.SHARING.name -> DashboardSteps.SHARING
            DashboardSteps.LETTER_CAMPAIGN.name -> DashboardSteps.LETTER_CAMPAIGN
            DashboardSteps.GOVERNMENT.name-> DashboardSteps.GOVERNMENT
            DashboardSteps.PROTEST.name -> DashboardSteps.PROTEST
            else -> DashboardSteps.RESEARCH
        }
    }

    @TypeConverter
    fun toKey(step : DashboardSteps): String {
        return when(step) {
            DashboardSteps.RESEARCH -> DashboardSteps.RESEARCH.name
            DashboardSteps.WRITE_LETTER -> DashboardSteps.WRITE_LETTER.name
            DashboardSteps.SHARING -> DashboardSteps.SHARING.name
            DashboardSteps.LETTER_CAMPAIGN -> DashboardSteps.LETTER_CAMPAIGN.name
            DashboardSteps.GOVERNMENT -> DashboardSteps.GOVERNMENT.name
            DashboardSteps.PROTEST -> DashboardSteps.PROTEST.name
        }
    }
}
