package com.kidssavetheocean.fatechanger.firebase.model

data class UsersModel(val campaign: Campaign? = null, val dash_joined_a_policy_hijack_campaign: Boolean = false, val dash_learn_about_problem: Boolean = false,
                      val dash_protest: Boolean = false, val dash_share: Boolean = false,
                      val dash_wrote_a_letter_about_climate: Boolean = false, val dash_wrote_a_letter_about_plastic: Boolean = false,
                      var hijack_policy_selected: String = "", val location_id: String = "", var signatures_pledged: Int = 0,
                      val user_letters_written: Int = 0, var user_person_type: Int = 0)