package com.kidssavetheocean.fatechanger.utility

import android.os.Build
import android.os.Bundle
import java.io.Serializable

const val EMPTY_STRING = ""

/**
 * gets a serializable from a bundle by checking the device Android version and using the appropriate method
 * in the future it is expected that Google will provide a BundleCompat feature to allow for not using the
 * deprecated method [getSerializable(key: String)].
 * Once this happens we will no longer need this function
 */
@Suppress("DEPRECATION")
inline fun <reified T : Serializable> Bundle.getSerializableCompat(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSerializable(key, T::class.java)
    } else {
        getSerializable(key) as? T
    }
}