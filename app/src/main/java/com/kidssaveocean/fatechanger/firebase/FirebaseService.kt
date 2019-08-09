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

    var hasCountries = false
        get() = countries.isNotEmpty()

    companion object {

        private val instance: FirebaseService = FirebaseService()
        /* The 'table' in Firebase database */
        @JvmField val COUNTRIES_TABLE = "COUNTRIES"
        /* Needed properties in that table */
        @JvmField val COUNTRY_NAME = "country_name"
        @JvmField val COUNTRY_ADDRESS = "country_address"
        @JvmField val COUNTRY_HEAD_OF_STATE_TITLE = "country_head_of_state_title"
        @JvmField val LATITUDE = "latitude"
        @JvmField val LONGITUDE = "longitude"
        @JvmField val LETTERS_WRITTEN_TO_COUNTRY = "letters_written_to_country"

        @Synchronized
        fun getInstance(): FirebaseService {
            return instance
        }
    }

    init {
        auth.signInAnonymously().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var dbInstance = FirebaseDatabase.getInstance().reference;
                dbInstance.child(COUNTRIES_TABLE).addListenerForSingleValueEvent(object : ValueEventListener {

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (item in dataSnapshot.children) {
                            val countryModel = CountryModel.create()
                            for (property in item.children) {
                                try {
                                    when (property.key) {
                                        COUNTRY_NAME -> countryModel.countryName = property?.value as String
                                        COUNTRY_ADDRESS -> countryModel.countryAddress = property?.value as String
                                        COUNTRY_HEAD_OF_STATE_TITLE -> countryModel.countryHeadOfStateTitle = property?.value as String
//                                        "country_number" -> countryModel.countryNumber = property?.value as Long
                                        LATITUDE -> countryModel.latitude = property?.value as Double
                                        LONGITUDE -> countryModel.longitude = property?.value as Double
                                        LETTERS_WRITTEN_TO_COUNTRY -> countryModel.lettersWrittenToCountry = property?.value as Long
                                        else -> println("Unknown parameter in firebase: " + property.value)
                                    }
                                }
                                catch (e: NoSuchElementException) {
                                    println("NoSuchElementException: " + e.message)
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

    fun  getCountries() : MutableCollection<CountryModel> {
        return  countries
    }
}