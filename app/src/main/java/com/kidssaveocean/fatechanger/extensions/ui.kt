package com.kidssaveocean.fatechanger.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/*
* Helps to add fragments to the navigation stack
* */
fun Fragment.addToNavigationStack(manager : FragmentManager, containerId : Int, tag : String = "") {
    val fragmentTransaction = manager.beginTransaction()
    when (tag) {
        "" -> fragmentTransaction.replace(containerId, this)
        else -> fragmentTransaction.replace(containerId, this , tag)
    }
    fragmentTransaction.addToBackStack(tag)
    fragmentTransaction.commit()
}