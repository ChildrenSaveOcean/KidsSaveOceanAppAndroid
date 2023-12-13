package com.kidssaveocean.fatechanger.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import com.kidssaveocean.fatechanger.utility.EMPTY_STRING

class FateChangerSharedPrefs(val context: Context) {
    private var sharedPrefs: SharedPreferences = context.getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE)

    fun getString(key: String) = sharedPrefs.getString(key, EMPTY_STRING)

    fun getBoolean(key: String, defaultValue: Boolean = false) = sharedPrefs.getBoolean(key, defaultValue)

    /**
     * Function for easy editing of shared preferences.
     * Contains internal apply() function
     * @param operation = the operations we want to apply on the shared prefs
     */
    fun edit(operation: SharedPreferences.Editor.() -> Unit){
        sharedPrefs.edit().run {
            operation()
            apply()
        }
    }
    companion object {
        const val SHARED_PREFS_FILE = "fateChangerPrefs"
        const val LAST_SELECTED_DASHBOARD_CONST = "lastSelected"
    }
}