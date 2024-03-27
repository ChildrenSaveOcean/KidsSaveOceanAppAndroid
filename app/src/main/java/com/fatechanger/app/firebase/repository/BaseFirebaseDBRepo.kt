package com.fatechanger.app.firebase.repository

import android.util.Log
import com.fatechanger.app.firebase.FirebaseFailedException
import com.fatechanger.app.service.FateResult
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

abstract class BaseFirebaseDBRepo<T : Any, R : Any>(private val tableName: String, private val clazz: Class<T>) {

    fun getData(onComplete: (FateResult<List<Pair<String, T>>>) -> Unit) {
        FirebaseDatabase.getInstance().reference.child(tableName)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError) {
                     onComplete(FateResult.Failure(FirebaseFailedException(databaseError.message)))
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("FirebaseService", "onDataChange")
                    val list = dataSnapshot.children
                        .filter { dataSnapshot.key != null && dataSnapshot.getValue(clazz) != null }
                        .map {
                            Pair(it.key!!, it.getValue(clazz)!!)
                        }
                    onComplete(FateResult.Success(list))
                }
            })
    }

    abstract fun handleData(list: List<Pair<String, T>>): R
}