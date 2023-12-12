package com.kidssaveocean.fatechanger.presentation.mvvm.vm

import android.os.Bundle
import androidx.databinding.Observable
import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel(), IViewModel, Observable {

    override fun postNavigationArgs(): Bundle? {
        //do nothing
        return null
    }

    override fun receiveNavigationArgs(args: Bundle?) {
        //do nothing
    }

    override fun onResume() {
        // implement when needed
    }

    override fun onPause() {
        // implement when needed
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        // implement if needed
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        // implement if needed
    }
}