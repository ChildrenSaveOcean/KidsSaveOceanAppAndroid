package com.kidssaveocean.fatechanger.database

import android.content.Context
import androidx.room.*
import com.kidssaveocean.fatechanger.dashboard.DashboardSteps
import com.kidssaveocean.fatechanger.database.dao.DashboardStepDao
import com.kidssaveocean.fatechanger.database.dao.KeyValueDao
import com.kidssaveocean.fatechanger.database.entities.DashboardStep
import com.kidssaveocean.fatechanger.database.entities.KeyValue

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

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}

class StepConverter {
    @TypeConverter
    fun toStep(key: String): DashboardSteps {
        when(key) {
            DashboardStep.STEP_1 -> return DashboardSteps.RESEARCH
            DashboardStep.STEP_2 -> return DashboardSteps.WRITE_LETTER
            DashboardStep.STEP_3 -> return DashboardSteps.SHARING
            DashboardStep.STEP_4 -> return DashboardSteps.LETTER_CAMPAING
            DashboardStep.STEP_5 -> return DashboardSteps.GOVERNMENT
            DashboardStep.STEP_6 -> return DashboardSteps.PROTEST
            else -> return DashboardSteps.RESEARCH
        }
    }

    @TypeConverter
    fun toKey(step : DashboardSteps): String {
        when(step) {
            DashboardSteps.RESEARCH -> return DashboardStep.STEP_1
            DashboardSteps.WRITE_LETTER -> return DashboardStep.STEP_2
            DashboardSteps.SHARING -> return DashboardStep.STEP_3
            DashboardSteps.LETTER_CAMPAING -> return DashboardStep.STEP_4
            DashboardSteps.GOVERNMENT -> return DashboardStep.STEP_5
            DashboardSteps.PROTEST -> return DashboardStep.STEP_6
            else -> return ""
        }
    }
}
