package com.kidssaveocean.fatechanger.policy

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kidssaveocean.fatechanger.R
import kotlinx.android.synthetic.main.fragment_policy_home.*

class PolicyHomeFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_policy_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imgHowToWork.setOnClickListener{
            startActivity(Intent(activity, PolicyVideoActivity::class.java))
        }
    }
}