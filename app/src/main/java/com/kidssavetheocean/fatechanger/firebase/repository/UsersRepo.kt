package com.kidssavetheocean.fatechanger.firebase.repository

import android.text.TextUtils
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kidssavetheocean.fatechanger.Constants
import com.kidssavetheocean.fatechanger.firebase.FirebaseFailedException
import com.kidssavetheocean.fatechanger.firebase.UserNotExsitException
import com.kidssavetheocean.fatechanger.firebase.model.UsersModel
import com.kidssavetheocean.fatechanger.service.FateResult

object UsersRepo : BaseFirebaseDBRepo<UsersModel, List<Pair<String, UsersModel>>>(Constants.TABLE_NAME_USERS, UsersModel::class.java) {

    var userModel: Pair<String, UsersModel?>? = null

    override fun handleData(list: List<Pair<String, UsersModel>>): List<Pair<String, UsersModel>> {
        return list
    }

    fun getUser(onComplete: (FateResult<Pair<String, UsersModel>>) -> Unit) {
        loginAndGetUid { fateResult ->
            fateResult.onSuccess {uid ->
                FirebaseDatabase.getInstance().reference.child(Constants.TABLE_NAME_USERS).child(uid)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(databaseError: DatabaseError) {
                            onComplete(FateResult.Failure(FirebaseFailedException(databaseError.message)))
                        }

                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            Log.d("FirebaseService", "onDataChange")
                            val key = dataSnapshot.key!!
                            val value = dataSnapshot.getValue(UsersModel::class.java)
                            if (value == null) {
                                onComplete(FateResult.Failure(UserNotExsitException()))
                            } else {
                                userModel = Pair(key, value)
                                onComplete(FateResult.Success(Pair(key, value)))
                            }
                        }
                    })
            }.onFailure {
                onComplete(FateResult.Failure(it))
            }

        }
    }

    private fun getUIDFromFirebase(): String? {
        return FirebaseAuth.getInstance().currentUser?.uid
    }

    private fun loginAndGetUid(onComplete: (FateResult<String>) -> Unit) {
        var uid = getUIDFromFirebase()
        if (TextUtils.isEmpty(uid)) {
            FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener { task ->
                FirebaseAuthCheckRepo.isAuth = task.isSuccessful
                uid = getUIDFromFirebase()

                if (task.isSuccessful && !TextUtils.isEmpty(uid)) {
                    onComplete(FateResult.Success(uid!!))
                } else {
                    onComplete(FateResult.Failure(FirebaseFailedException("Authentication failed")))
                }
            }
        } else {
            onComplete(FateResult.Success(uid!!))
        }
    }

    fun updateOrCreateUser(user: UsersModel?) {
        getUIDFromFirebase()?.let {
            if (it.isNotEmpty()) {
                FirebaseDatabase.getInstance().reference.child(Constants.TABLE_NAME_USERS).child(it).apply {
                    push()
                    user?.apply {
                        userModel = Pair(it, user)
                        campaign?.also { campaign ->
                            child("campaign").child("campaign_id").setValue(campaign.campaign_id)
                            child("campaign").child("signatures_collected").setValue(campaign.signatures_collected)
                        }
                        child("dash_joined_a_policy_hijack_campaign").setValue(dash_joined_a_policy_hijack_campaign)
                        child("dash_learn_about_problem").setValue(dash_learn_about_problem)
                        child("dash_protest").setValue(dash_protest)
                        child("dash_share").setValue(dash_share)
                        child("hijack_policy_selected").setValue(hijack_policy_selected)
                        child("location_id").setValue(location_id)
                        child("dash_wrote_a_letter_about_climate").setValue(dash_wrote_a_letter_about_climate)
                        child("dash_wrote_a_letter_about_plastic").setValue(dash_wrote_a_letter_about_plastic)
                        child("signatures_pledged").setValue(signatures_pledged)
                        child("user_letters_written").setValue(user_letters_written)
                        child("user_person_type").setValue(user_person_type)
                    }
                }
            }
        }
    }
}