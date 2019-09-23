package com.kidssaveocean.fatechanger.database.dao

import androidx.room.*
import com.kidssaveocean.fatechanger.dashboard.DashboardSteps

import com.kidssaveocean.fatechanger.database.entities.DashboardStep

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
