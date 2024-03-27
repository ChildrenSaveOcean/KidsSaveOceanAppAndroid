package com.fatechanger.app.presentation.mvvm.vm

import com.fatechanger.app.presentation.AbstractViewModel
import com.fatechanger.app.service.coroutines.ICoroutineContextProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EmptyViewModel @Inject constructor(contextProvider: ICoroutineContextProvider) :
    AbstractViewModel(contextProvider)
