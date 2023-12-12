package com.kidssaveocean.fatechanger.presentation.mvvm.vm

import com.kidssaveocean.fatechanger.presentation.AbstractViewModel
import com.kidssaveocean.fatechanger.service.coroutines.ICoroutineContextProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EmptyViewModel @Inject constructor(contextProvider: ICoroutineContextProvider) :
    AbstractViewModel(contextProvider)
