package com.kidssaveocean.fatechanger.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.kidssaveocean.fatechanger.firebase.model.CountryModel
import java.util.*

class FirebaseService : Observable ()  {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _countries : MutableCollection<CountryModel> = mutableListOf()

    //todo what ??
//    val countriesObservable = PublishSubject.create<List<CountryModel>>()

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
                Log.d("FirebaseService", task.result?.user?.uid ?: "UID not available")
                var dbInstance = FirebaseDatabase.getInstance().reference;
                dbInstance.child(COUNTRIES_TABLE).addListenerForSingleValueEvent(object : ValueEventListener {

                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        for (item in dataSnapshot.children) {
                            var countryModel = item.getValue(CountryModel::class.java)
                            countryModel?.let {
                                it.country_code = item.key!!
                                _countries.add(it)
                                setChanged()
                            }
                        }
                        notifyObservers(countries)
//                        countriesObservable.onNext(countries)
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        println("The read failed: " + databaseError.code)
                    }
                })
            }
        }
    }

    fun increaseWrittenLettersNumber(country : CountryModel) {

        var writtenLetters = country.letters_written_to_country + 1

        val dbObject: HashMap<String, Any> = hashMapOf(
                "country_name" to country.country_name,
                "country_number" to country.country_number,
                "country_address" to country.country_address,
                "country_head_of_state_title" to country.country_head_of_state_title,
                "latitude" to country.latitude,
                "longitude" to country.longitude,
                "letters_written_to_country" to writtenLetters
        )

        try {
            FirebaseDatabase
                    .getInstance()
                    .reference
                    .child(COUNTRIES_TABLE)
                    .child(country.country_code)
                    .setValue(dbObject)
        }
        catch (dE: DatabaseException) {
            print("Firebase exception: ${dE.message}")
        }
    }
}