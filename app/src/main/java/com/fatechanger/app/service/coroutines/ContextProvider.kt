package com.fatechanger.app.service.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class ContextProvider: ICoroutineContextProvider {
    override val Main: CoroutineContext by lazy { Dispatchers.Main }
    override val IO: CoroutineContext by lazy { Dispatchers.IO }
    override val Default: CoroutineContext by lazy { Dispatchers.Default }

    override val backgroundScope: CoroutineScope = object : CoroutineScope {
        override val coroutineContext: CoroutineContext = Default
    }
}
