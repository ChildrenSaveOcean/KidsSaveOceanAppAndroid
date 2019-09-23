package com.kidssaveocean.fatechanger.dashboard

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.bottomNavigation.BottomNavigationActivity
import com.kidssaveocean.fatechanger.extensions.addToNavigationStack
import com.kidssaveocean.fatechanger.resources.ResourcesFragment
import kotlinx.android.synthetic.main.fragment_start_activist_campaign.*

class StartActivistCampaignFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start_activist_campaign, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tools_button?.setOnClickListener {
            ResourcesFragment("https://www.kidssaveocean.com/studentresources").addToNavigationStack(
                    (activity as BottomNavigationActivity)?.supportFragmentManager,
                    R.id.fragment_container)
        }
    }
}
