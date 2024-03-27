package com.fatechanger.app.database.dao

import androidx.room.*
import com.fatechanger.app.dashboard.DashboardSteps

import com.fatechanger.app.database.entities.DashboardStep

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
