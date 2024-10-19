package com.kidssavetheocean.fatechanger.service.coroutines

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoroutinesModule {

    @Provides
    @Singleton
    internal fun provideContextProvider(): ICoroutineContextProvider = ContextProvider()
}