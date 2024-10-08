package com.kidssavetheocean.fatechanger

import android.app.Application
import android.util.Log
import com.kidssavetheocean.fatechanger.dagger.component.DaggerHijackPolicyComponent
import com.kidssavetheocean.fatechanger.firebase.UserNotExsitException
import com.kidssavetheocean.fatechanger.firebase.model.Campaign
import com.kidssavetheocean.fatechanger.firebase.model.UsersModel
import com.kidssavetheocean.fatechanger.firebase.repository.UsersRepo
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() {
    @Inject
    lateinit var usersRepo: UsersRepo

    override fun onCreate() {
        super.onCreate()
        DaggerHijackPolicyComponent.builder().build().inject(this)
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            usersRepo.getUser { fateResult ->
                fateResult.onSuccess {
                    Log.d("MainApplication", "$it")
                }.onFailure {
                    Log.d("MainApplication", "$it")
                    when (it) {
                        is UserNotExsitException -> {
                            usersRepo.updateOrCreateUser(UsersModel(Campaign()))
                        }
                        else -> {

                        }
                    }
                }
            }
        }
    }
}