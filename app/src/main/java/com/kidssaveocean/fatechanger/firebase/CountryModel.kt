package com.kidssaveocean.fatechanger.firebase

data class CountryModel (val countryCode : String = "") {
    companion object Factory {
        fun create(): CountryModel = CountryModel()
    }
    var countryName : String = ""
    var countryNumber : Long = 0
    var countryAddress : String? = null
    var countryHeadOfStateTitle : String? = null
    var latitude : Double = 0.0
    var longitude : Double = 0.0
    var lettersWrittenToCountry : Long = 0
}