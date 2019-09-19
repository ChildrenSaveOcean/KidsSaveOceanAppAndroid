package com.kidssaveocean.fatechanger.database.entities


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.kidssaveocean.fatechanger.dashboard.DashboardSteps
import com.kidssaveocean.fatechanger.database.AppDatabase.Companion.DASHBOARD_STEPS_TABLE

@Entity(tableName = DASHBOARD_STEPS_TABLE)
data class DashboardStep (
        @PrimaryKey @ColumnInfo(name = "step") @TypeConverters
        val step: DashboardSteps,
        @ColumnInfo(name = "completed")
        val completed: Boolean
) {

    companion object {
        const val STEP_1 = "STEP_1"
        const val STEP_2 = "STEP_2"
        const val STEP_3 = "STEP_3"
        const val STEP_4 = "STEP_4"
        const val STEP_5 = "STEP_5"
        const val STEP_6 = "STEP_6"
    }

}