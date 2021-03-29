package com.kidssaveocean.fatechanger.common

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.Disposable

open class BaseActivity : AppCompatActivity() {
    private val disposableList = mutableListOf<Disposable>()
    private val networkReceiver = NetworkReceiver()

    override fun onResume() {
        super.onResume()
        registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    fun disposableOnDestroy(disposable: Disposable) {
        disposableList.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(networkReceiver)
        disposableList.map {
            it.dispose()
        }
    }

    open fun isNetworkConnected(): Boolean {
        if (this != null) {
            val mNetworkInfo = (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable && mNetworkInfo.isConnected
            }
        }
        return false
    }

    open fun networkChangeConnect() {}

    inner class NetworkReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val manager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = manager.activeNetworkInfo

            if (networkInfo != null && networkInfo.isAvailable && networkInfo.isConnected) {
                networkChangeConnect()
            }
        }
    }

}

