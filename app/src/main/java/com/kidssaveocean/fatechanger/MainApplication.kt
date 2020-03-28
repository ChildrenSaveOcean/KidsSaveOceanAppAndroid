package com.kidssaveocean.fatechanger

import android.app.Application
import android.util.Log
import com.kidssaveocean.fatechanger.dagger.component.DaggerHijackPolicyComponent
import com.kidssaveocean.fatechanger.firebase.UserNotExsitException
import com.kidssaveocean.fatechanger.firebase.model.Campaign
import com.kidssaveocean.fatechanger.firebase.model.UsersModel
import com.kidssaveocean.fatechanger.firebase.repository.UsersRepo
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class MainApplication : Application() {
    @Inject
    lateinit var usersRepo: UsersRepo
    var disposables = mutableListOf<Disposable>()

    override fun onCreate() {
        super.onCreate()
        DaggerHijackPolicyComponent.builder().build().inject(this)
        disposables.add(usersRepo.getUser().subscribe({
            Log.d("MainApplication", "$it")
        }, {
            Log.d("MainApplication", "$it")
            when (it) {
                is UserNotExsitException -> {
                    usersRepo.updateOrCreateUser(UsersModel(Campaign()))
                }
                else -> {

                }
            }
        }))
    }

    override fun onTerminate() {
        super.onTerminate()
        disposables.map {
            it.dispose()
        }
    }

}