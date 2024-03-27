package com.fatechanger.app.firebase.model

data class CountryModel (
    var country_code : String = "",
    val country_name : String = "",
    val country_number : Long = 0,
    val country_address : String = "",
    val country_head_of_state_title : String = "",
    val latitude : Double = 0.0,
    val longitude : Double = 0.0,
    val letters_written_to_country : Long = 0
)