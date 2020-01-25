package com.kidssaveocean.fatechanger.firebase.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable

open class BaseViewModel: ViewModel() {
    val disposables = mutableListOf<Disposable>()

    override fun onCleared() {
        super.onCleared()
        disposables.map {
            it.dispose()
        }
    }
}