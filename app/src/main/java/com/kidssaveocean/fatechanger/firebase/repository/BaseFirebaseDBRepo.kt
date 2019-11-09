package com.kidssaveocean.fatechanger.firebase.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kidssaveocean.fatechanger.firebase.FirebaseFailedException
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

@Suppress("NAME_SHADOWING")
abstract class BaseFirebaseDBRepo<T : Any, R : Any>(private val tableName: String, clazz: Class<T>) {

    private val subject = BehaviorSubject.create<List<Pair<String, T>>>()

    init {
        Log.d("FirebaseService", "init")
        FirebaseDatabase.getInstance().reference.child(tableName)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(databaseError: DatabaseError) {
                        subject.onError(FirebaseFailedException())
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        Log.d("FirebaseService", "onDataChange")
                        val list = dataSnapshot.children
                                .filter { dataSnapshot.key != null && dataSnapshot.getValue(clazz) != null }
                                .map {
                                    Pair(it.key!!, it.getValue(clazz)!!)
                                }
                        subject.onNext(list)
                    }
                })
    }

    fun getDataObservable(): Observable<R> {
        Log.d("FirebaseService", "getDataObservable")
        return Observable.create<List<Pair<String, T>>> { emitter ->
            subject.subscribe {
                emitter.onNext(it)
            }
        }.map {
            handleData(it)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    abstract fun handleData(list: List<Pair<String, T>>): R
}
