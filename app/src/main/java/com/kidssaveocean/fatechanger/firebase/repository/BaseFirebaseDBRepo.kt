package com.kidssaveocean.fatechanger.firebase.repository

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kidssaveocean.fatechanger.firebase.FirebaseFailedException
import com.kidssaveocean.fatechanger.service.FateResult

abstract class BaseFirebaseDBRepo<T : Any, R : Any>(private val tableName: String, private val clazz: Class<T>) {

    fun getData() {
        //todo fix
        FirebaseDatabase.getInstance().reference.child(tableName)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError) {
                     FateResult.Failure(FirebaseFailedException(databaseError.message))
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("FirebaseService", "onDataChange")
                    val list = dataSnapshot.children
                        .filter { dataSnapshot.key != null && dataSnapshot.getValue(clazz) != null }
                        .map {
                            Pair(it.key!!, it.getValue(clazz)!!)
                        }
                    FateResult.Success(list)
                }
            })
//        Single.create<List<Pair<String, T>>> { emitter ->
//            FirebaseDatabase.getInstance().reference.child(tableName)
//                    .addListenerForSingleValueEvent(object : ValueEventListener {
//                        override fun onCancelled(databaseError: DatabaseError) {
//                            emitter.onError(FirebaseFailedException(databaseError.message))
//                        }
//
//                        override fun onDataChange(dataSnapshot: DataSnapshot) {
//                            Log.d("FirebaseService", "onDataChange")
//                            val list = dataSnapshot.children
//                                    .filter { dataSnapshot.key != null && dataSnapshot.getValue(clazz) != null }
//                                    .map {
//                                        Pair(it.key!!, it.getValue(clazz)!!)
//                                    }
//                            emitter.onSuccess(list)
//                        }
//                    })
//        }.map {
//            handleData(it)
//        }.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
    }

    abstract fun handleData(list: List<Pair<String, T>>): R
}
