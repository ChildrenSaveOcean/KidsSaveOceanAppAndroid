package com.kidssaveocean.fatechanger.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FirebaseService private constructor() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val countries : Collection<CountryModel> = listOf()

    init {
        auth.signInAnonymously().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var dbInstance = FirebaseDatabase.getInstance().reference;
                dbInstance.child("COUNTRIES").addListenerForSingleValueEvent(object : ValueEventListener {

                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        val items = dataSnapshot.children.iterator()
                        if (items.hasNext()) {
                            val countryModel = CountryModel.create()
                            val index = items.next()
                            val itemsIterator = index.children.iterator()
                            while (itemsIterator.hasNext()) {
                                val currentItem = itemsIterator.next()
                                when (currentItem.key) {
                                    "country_name" -> countryModel.countryName = currentItem.value as String
                                    "country_address" -> countryModel.countryAddress = currentItem.value as String
                                    "country_head_of_state_title" -> countryModel.countryHeadOfStateTitle = currentItem.value as String
                                    "country_number" -> countryModel.countryNumber = currentItem.value as Long
                                    "latitude" -> countryModel.latitude = currentItem.value as Double
                                    "longitude" -> countryModel.longitude = currentItem.value as Double
                                    "letters_written_to_country" -> countryModel.lettersWrittenToCountry = currentItem.value as Long
                                    else -> println("Unknown parameter in firebase: " + currentItem.value as String)
                                }
                            }
                            countries.plus(countryModel)
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        println("The read failed: " + databaseError.code)
                    }
                })
            }
        }
    }

    companion object {
        private val instance: FirebaseService = FirebaseService()

        @Synchronized
        fun getInstance(): FirebaseService {
            return instance
        }
    }

    fun  getCountries() : Collection<CountryModel> {
        return  countries
    }
}