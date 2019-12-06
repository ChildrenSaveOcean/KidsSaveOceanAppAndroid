package com.kidssaveocean.fatechanger.firebase.repository

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Singleton

@Singleton
object FirebaseAuthCheckRepo {
    var isAuth: Boolean = false

    fun signIn(){
        if (!isAuth){
            FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener { task ->
                isAuth = task.isSuccessful
            }
        }
    }
}