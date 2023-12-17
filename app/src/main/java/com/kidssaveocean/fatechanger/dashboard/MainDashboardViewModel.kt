package com.kidssaveocean.fatechanger.dashboard

import android.content.Context
import com.kidssaveocean.fatechanger.dashboard.DashboardSteps.GOVERNMENT
import com.kidssaveocean.fatechanger.dashboard.DashboardSteps.LETTER_CAMPAIGN
import com.kidssaveocean.fatechanger.dashboard.DashboardSteps.PROTEST
import com.kidssaveocean.fatechanger.dashboard.DashboardSteps.RESEARCH
import com.kidssaveocean.fatechanger.dashboard.DashboardSteps.SHARING
import com.kidssaveocean.fatechanger.dashboard.DashboardSteps.WRITE_LETTER
import com.kidssaveocean.fatechanger.presentation.AbstractViewModel
import com.kidssaveocean.fatechanger.service.coroutines.ICoroutineContextProvider
import com.kidssaveocean.fatechanger.sharedprefs.FateChangerSharedPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainDashboardViewModel @Inject constructor(@ApplicationContext context: Context, contextProvider: ICoroutineContextProvider) :
    AbstractViewModel(contextProvider) {

    private var prefs: FateChangerSharedPrefs = FateChangerSharedPrefs(context)
    val tasks: List<DashboardTask> = listOf(
        DashboardTask(RESEARCH),
        DashboardTask(WRITE_LETTER),
        DashboardTask(SHARING),
        DashboardTask(LETTER_CAMPAIGN),
        DashboardTask(GOVERNMENT),
        DashboardTask(PROTEST)
    )

    var lastSelected = tasks.first().type
        private set

    init {
        loadStepsProgress()
        loadLastSelectedFromPrefs()
    }

    private fun saveStepsProgress(task: DashboardTask) {
        CoroutineScope(Dispatchers.IO).launch {
            val type = task.type.name
            prefs.edit { putBoolean(type, task.getIsCompleted()) }
        }
    }

    private fun loadStepsProgress() {
        CoroutineScope(Dispatchers.IO).launch {
            tasks.forEach {
                it.setIsCompleted(prefs.getBoolean(it.type.name))
            }
        }
    }

    private fun saveLastSelectedInPrefs() {
        prefs.edit {
            putString(FateChangerSharedPrefs.LAST_SELECTED_DASHBOARD_CONST, lastSelected.name)
        }
    }

    private fun loadLastSelectedFromPrefs() {
        val result = prefs.getString(FateChangerSharedPrefs.LAST_SELECTED_DASHBOARD_CONST)
        lastSelected = when (result) {
            RESEARCH.name -> RESEARCH
            WRITE_LETTER.name -> WRITE_LETTER
            SHARING.name -> SHARING
            LETTER_CAMPAIGN.name -> LETTER_CAMPAIGN
            GOVERNMENT.name -> GOVERNMENT
            PROTEST.name -> PROTEST
            else -> RESEARCH
        }
    }

    fun updateTaskStatus() = tasks.find { it.type == lastSelected }?.reverseIsCompleted()

    fun saveLastSelectedIcon(step: DashboardSteps) {
        lastSelected = step
    }

    fun onDestroy() {
        tasks.forEach { saveStepsProgress(it) }
        saveLastSelectedInPrefs()
    }
}