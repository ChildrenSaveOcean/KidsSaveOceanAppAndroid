package com.kidssaveocean.fatechanger.policy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kidssaveocean.fatechanger.firebase.repository.BaseFirebaseDBRepo

object PolicyStepsRepo: BaseFirebaseDBRepo<String, List<String>>("", String::class.java) {

    override fun handleData(list: List<Pair<String, String>>): List<String> {
        return list.map {
            it.second
        }
    }

    val list = mutableListOf<String>().apply {
        add("Watch the video about the ballot process to understand how it works.")
        add("Vote on which policy you want passed.")
        add("After kids decide, you’ll be notified. Check out the policy, then enter in the app how many signatures you plan to collect.")
        add("Download and print the signature collection paper. Begin collecting. Share.")
        add("As you collect signatures, keep them updated - see how many we need.")
        add("We can’t do this alone; we must recruit other organizations to help - kids will lead, adults will follow. Tell your local newspaper,  environmenal groups, your friends!")
        add("You’ll be notified when and where to mail your signature papers.")
    }

    private val steps: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>().also {
            it.value = list
        }
    }

    fun getSteps(): LiveData<List<String>>{
        return steps
    }
}