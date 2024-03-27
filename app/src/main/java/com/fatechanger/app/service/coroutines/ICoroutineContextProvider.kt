package com.fatechanger.app.service.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

interface ICoroutineContextProvider {
    val Main: CoroutineContext
    val IO: CoroutineContext
    val Default: CoroutineContext
    val backgroundScope: CoroutineScope
}