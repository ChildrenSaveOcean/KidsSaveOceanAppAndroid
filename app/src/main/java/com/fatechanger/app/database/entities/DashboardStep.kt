package com.fatechanger.app.database.entities


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.fatechanger.app.dashboard.DashboardSteps
import com.fatechanger.app.database.AppDatabase.Companion.DASHBOARD_STEPS_TABLE

@Entity(tableName = DASHBOARD_STEPS_TABLE)
data class DashboardStep (
        @PrimaryKey @ColumnInfo(name = "step") @TypeConverters
        val step: DashboardSteps,
        /* The column represents "I did it!" button or "I did it about climate" */
        @ColumnInfo(name = "first_completed_step")
        val first_completed_step: Boolean,
        /* The column represents "I did it about plastic" button */
        @ColumnInfo(name = "second_completed_step")
        val second_completed_step: Boolean
)