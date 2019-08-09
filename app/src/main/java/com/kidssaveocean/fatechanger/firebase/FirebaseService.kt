package com.kidssaveocean.fatechanger.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

class FirebaseService : Observable ()  {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val countries : MutableCollection<CountryModel> = mutableListOf()

    init {
        auth.signInAnonymously().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var dbInstance = FirebaseDatabase.getInstance().reference;
                dbInstance.child("COUNTRIES").addListenerForSingleValueEvent(object : ValueEventListener {

                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        for (item in dataSnapshot.children) {
                            val countryModel = CountryModel.create()
                            for (property in item.children) {

                                try {
                                    when (property.key) {
                                        "country_name" -> countryModel.countryName = property?.value as String
                                        "country_address" -> countryModel.countryAddress = property?.value as String
                                        "country_head_of_state_title" -> countryModel.countryHeadOfStateTitle = property?.value as String
//                                        "country_number" -> countryModel.countryNumber = property?.value as Long
                                        "latitude" -> countryModel.latitude = property?.value as Double
                                        "longitude" -> countryModel.longitude = property?.value as Double
                                        "letters_written_to_country" -> countryModel.lettersWrittenToCountry = property?.value as Long
                                        else -> println("Unknown parameter in firebase: " + property.value)
                                    }
                                }
                                catch (e: NoSuchElementException) {

                                }
                            }
                            countries.add(countryModel)
                            setChanged()
                        }
                        notifyObservers(countries)
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

    fun  getCountries() : MutableCollection<CountryModel> {
        return  countries
    }
}