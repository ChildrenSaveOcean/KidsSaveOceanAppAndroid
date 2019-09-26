package com.kidssaveocean.fatechanger.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.bottomNavigation.BottomNavigationActivity
import com.kidssaveocean.fatechanger.extensions.addToNavigationStack
import com.kidssaveocean.fatechanger.resources.ResourcesFragment
import kotlinx.android.synthetic.main.fragment_nobodys_listening.*

class NobodysListeningFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nobodys_listening, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomActivity = activity as BottomNavigationActivity
        golden_rules_button?.setOnClickListener {
            GoldenRulesFragment().addToNavigationStack(
                    bottomActivity.supportFragmentManager,
                    R.id.fragment_container)
        }

        activist_toolkit_button?.setOnClickListener {
            ResourcesFragment("https://www.kidssaveocean.com/studentresources").addToNavigationStack(
                    bottomActivity.supportFragmentManager,
                    R.id.fragment_container)
        }
    }
}
