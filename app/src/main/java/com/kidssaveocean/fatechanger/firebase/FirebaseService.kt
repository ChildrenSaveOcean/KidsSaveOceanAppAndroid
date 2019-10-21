package com.kidssaveocean.fatechanger.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kidssaveocean.fatechanger.firebase.model.CountryModel
import io.reactivex.subjects.PublishSubject
import java.util.*

class FirebaseService : Observable ()  {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _countries : MutableCollection<CountryModel> = mutableListOf()

    val countriesObservable = PublishSubject.create<List<CountryModel>>()

    val countries : List<CountryModel>
        get() = _countries.toList()

    var hasCountries = false
        get() = _countries.isNotEmpty()

    companion object {

        private val instance: FirebaseService = FirebaseService()
        /* The 'table' in Firebase database */
        const val COUNTRIES_TABLE = "COUNTRIES"

        @Synchronized
        fun getInstance(): FirebaseService {
            return instance
        }
    }

    init {
        Log.d("FirebaseService", "init")
        auth.signInAnonymously().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var dbInstance = FirebaseDatabase.getInstance().reference;
                dbInstance.child(COUNTRIES_TABLE).addListenerForSingleValueEvent(object : ValueEventListener {

                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        for (item in dataSnapshot.children) {
                            var countryModel = item.getValue(CountryModel::class.java)
                            countryModel?.let {
                                _countries.add(it)
                                setChanged()
                            }
                        }
                        notifyObservers(countries)
                        countriesObservable.onNext(countries)
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        println("The read failed: " + databaseError.code)
                    }
                })
            }
        }
    }
}