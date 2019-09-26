package com.kidssaveocean.fatechanger.common

import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.Disposable

open class BaseActivity : AppCompatActivity() {
    private val disposableList = mutableListOf<Disposable>()

    fun disposableOnDestroy(disposable: Disposable) {
        disposableList.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposableList.map {
            it.dispose()
        }
    }
}