package com.kidssaveocean.fatechanger

import android.app.Application
import android.util.Log
import com.kidssaveocean.fatechanger.dagger.component.DaggerHijackPolicyComponent
import com.kidssaveocean.fatechanger.firebase.UserNotExsitException
import com.kidssaveocean.fatechanger.firebase.model.Campaign
import com.kidssaveocean.fatechanger.firebase.model.UsersModel
import com.kidssaveocean.fatechanger.firebase.repository.UsersRepo
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() {
    @Inject
    lateinit var usersRepo: UsersRepo

    override fun onCreate() {
        super.onCreate()
        DaggerHijackPolicyComponent.builder().build().inject(this)
        //todo fix
//        usersRepo.getUser().subscribe({
//            Log.d("MainApplication", "$it")
//        }, {
//            Log.d("MainApplication", "$it")
//            when (it) {
//                is UserNotExsitException -> {
//                    usersRepo.updateOrCreateUser(UsersModel(Campaign()))
//                }
//                else -> {
//
//                }
//            }
//        }
    }
}