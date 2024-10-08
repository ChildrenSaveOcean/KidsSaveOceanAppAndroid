package com.kidssavetheocean.fatechanger.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kidssavetheocean.fatechanger.dashboard.DashboardSteps
import com.kidssavetheocean.fatechanger.database.entities.DashboardStep

@Dao
interface DashboardStepDao {

    @Query("SELECT * FROM DashboardSteps WHERE step = :step")
    fun getDashboardStep(step: DashboardSteps): DashboardStep?

    @Query("SELECT * FROM DashboardSteps")
    fun getAllDashboardSteps(): List<DashboardStep>

    @Insert
    fun insertDashboardStep(dashboardStep: DashboardStep)

    @Delete
    fun deleteDashboardStep(dashboardStep: DashboardStep)

    @Update
    fun updateDashboardStep(dashboardStep: DashboardStep)
}
