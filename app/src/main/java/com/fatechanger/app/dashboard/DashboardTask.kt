package com.fatechanger.app.dashboard

class DashboardTask(val type: DashboardSteps, private var isCompleted: Boolean = false) {

    fun setIsCompleted(status: Boolean) {
        isCompleted = status
    }

    fun getIsCompleted() = isCompleted

    /**
     * Reverses the value of isCompleted.
     * Sets it to true if it was false or sets it to false if it was true
     * @return the new status
     */
    fun reverseIsCompleted(): Boolean {
        isCompleted = !isCompleted
        return isCompleted
    }
}
