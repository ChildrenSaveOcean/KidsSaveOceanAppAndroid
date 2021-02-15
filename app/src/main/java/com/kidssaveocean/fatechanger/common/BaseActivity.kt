package com.kidssaveocean.fatechanger.common

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.Disposable


open class BaseActivity : AppCompatActivity() {
    private val disposableList = mutableListOf<Disposable>()

    override fun onResume() {
        super.onResume()
    }

    fun disposableOnDestroy(disposable: Disposable) {
        disposableList.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposableList.map {
            it.dispose()
        }
    }


    open fun isNetworkConnected(): Boolean {
        if (this != null) {
            val mNetworkInfo = (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
            if (mNetworkInfo != null) {
                return mNetworkInfo.isConnected
            }
        }
        return false
    }

}