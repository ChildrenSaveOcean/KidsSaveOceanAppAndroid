package com.kidssaveocean.fatechanger.presentation

import com.kidssaveocean.fatechanger.presentation.mvvm.vm.BaseViewModel
import com.kidssaveocean.fatechanger.service.coroutines.ICoroutineContextProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
open class AbstractViewModel @Inject constructor(private val contextProvider: ICoroutineContextProvider): BaseViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext by lazy {
        contextProvider.Main + Job()
    }

    val backgroundScope: CoroutineScope by lazy {
        contextProvider.backgroundScope
    }

    override fun onCleared() {
        coroutineContext.cancel()
        super.onCleared()
    }
}