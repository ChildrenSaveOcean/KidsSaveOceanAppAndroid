package com.kidssaveocean.fatechanger.dashboard


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.bottomNavigation.BottomNavigationActivity
import com.kidssaveocean.fatechanger.extensions.addToNavigationStack
import com.kidssaveocean.fatechanger.resources.ResourcesFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_start_activist_campaign.tools_button

@AndroidEntryPoint
class EngageLocalGovernmentFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_engage_local_government, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tools_button?.setOnClickListener {
            (activity as? BottomNavigationActivity)?.supportFragmentManager?.let { fragmentManager ->
                ResourcesFragment("https://pederhill.wixsite.com/kids-save-ocean/studentresources").addToNavigationStack(
                    fragmentManager,
                    R.id.fragment_container
                )
            }
        }
    }
}
