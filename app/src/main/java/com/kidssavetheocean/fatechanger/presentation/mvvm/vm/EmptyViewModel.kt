package com.kidssavetheocean.fatechanger.presentation.mvvm.vm

import com.kidssavetheocean.fatechanger.presentation.AbstractViewModel
import com.kidssavetheocean.fatechanger.service.coroutines.ICoroutineContextProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EmptyViewModel @Inject constructor(contextProvider: ICoroutineContextProvider) :
    AbstractViewModel(contextProvider)
