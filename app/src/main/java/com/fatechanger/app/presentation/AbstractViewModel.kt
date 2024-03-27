package com.fatechanger.app.presentation

import com.fatechanger.app.presentation.mvvm.vm.BaseViewModel
import com.fatechanger.app.service.coroutines.ICoroutineContextProvider
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