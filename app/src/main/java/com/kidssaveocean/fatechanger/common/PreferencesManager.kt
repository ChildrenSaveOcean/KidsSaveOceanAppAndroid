package com.kidssaveocean.fatechanger.common

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.kidssaveocean.fatechanger.App
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object PrefsManager {

    private val sharedPrefs by lazy {
        App.appContext.getSharedPreferences("KidsSaveOceanApp", Context.MODE_PRIVATE)
    }

    var isOnBoardingComplete: Boolean by Preference(sharedPrefs, "is_onboarding_complete_key", false)



}


class Preference<T>(private val prefs: SharedPreferences,
                    private val name: String,
                    private val default: T): ReadWriteProperty<Any?, T> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreference(name, default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(name, value)
    }

    private fun <T> findPreference(name: String, default: T): T = with(prefs) {
        val res: Any = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> throw IllegalArgumentException("This type cannot be saved into Preferences")
        }
        @Suppress("UNCHECKED_CAST")
        res as T
    }

    @SuppressLint("CommitPrefEdits")
    private fun <T> putPreference(name: String, value: T) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> throw IllegalArgumentException("This type cannot be saved into Preferences")
        }.apply()
    }
}