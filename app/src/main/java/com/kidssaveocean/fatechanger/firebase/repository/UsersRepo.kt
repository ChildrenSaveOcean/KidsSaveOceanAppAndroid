package com.kidssaveocean.fatechanger.firebase.repository

import android.text.TextUtils
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kidssaveocean.fatechanger.Constants
import com.kidssaveocean.fatechanger.firebase.FirebaseFailedException
import com.kidssaveocean.fatechanger.firebase.UserNotExsitException
import com.kidssaveocean.fatechanger.firebase.model.UsersModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object UsersRepo : BaseFirebaseDBRepo<UsersModel, List<Pair<String, UsersModel>>>(Constants.TABLE_NAME_USERS, UsersModel::class.java) {
    var userModel: Pair<String, UsersModel?>? = null

    override fun handleData(list: List<Pair<String, UsersModel>>): List<Pair<String, UsersModel>> {
        return list
    }

    fun getUser(): Single<Pair<String, UsersModel>> {
        return loginAndGetUid().retry(1).flatMap { uid ->
            Single.create<Pair<String, UsersModel>> { emitter ->
                FirebaseDatabase.getInstance().reference.child(Constants.TABLE_NAME_USERS).child(uid)
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onCancelled(databaseError: DatabaseError) {
                                emitter.onError(FirebaseFailedException(databaseError.message))
                            }

                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                Log.d("FirebaseService", "onDataChange")
                                val key = dataSnapshot.key!!
                                val value = dataSnapshot.getValue(UsersModel::class.java)
                                if (value == null) {
                                    emitter.onError(UserNotExsitException())
                                } else {
                                    userModel = Pair(key, value)
                                    emitter.onSuccess(Pair(key, value))
                                }
                            }
                        })
            }
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getUIDFromFirebase(): String? {
        return FirebaseAuth.getInstance().currentUser?.uid
    }

    private fun loginAndGetUid(): Single<String> {
        return Single.create<String> {
            var uid = getUIDFromFirebase()
            if (TextUtils.isEmpty(uid)) {
                FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener { task ->
                    FirebaseAuthCheckRepo.isAuth = task.isSuccessful
                    uid = getUIDFromFirebase()
                    if (task.isSuccessful && !TextUtils.isEmpty(uid)) {
                        it.onSuccess(uid!!)
                    } else {
                        it.onError(FirebaseFailedException("Authentication failed"))
                    }
                }
            } else {
                it.onSuccess(uid!!)
            }
        }
    }

    fun updateOrCreateUser(user: UsersModel?) {
            getUIDFromFirebase()?.let {
                if (it.isNotEmpty()) {
                    FirebaseDatabase.getInstance().reference.child(Constants.TABLE_NAME_USERS).child(it).apply {
                        push()
                        user?.apply {
                            userModel = Pair(it, user)
                            campaign?.also {campaign ->
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