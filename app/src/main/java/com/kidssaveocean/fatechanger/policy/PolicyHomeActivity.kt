package com.kidssaveocean.fatechanger.policy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kidssaveocean.fatechanger.R

class PolicyHomeActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_policy_fragment)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment, PolicyHomeFragment())
        transaction.commit()
    }
}