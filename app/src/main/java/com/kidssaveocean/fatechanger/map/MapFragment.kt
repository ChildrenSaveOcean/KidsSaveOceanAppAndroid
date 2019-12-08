package com.kidssaveocean.fatechanger.map


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
import com.kidssaveocean.fatechanger.R

import com.kidssaveocean.fatechanger.bottomNavigation.BottomNavigationActivity
import com.kidssaveocean.fatechanger.extensions.addToNavigationStack
import kotlinx.android.synthetic.main.fragment_map.*



class MapFragment : Fragment() {
    var manager: FragmentManager? = null
    var transaction: FragmentTransaction? = null


    var mapShownFragment = MapShownFragment()
    var countryListFragment = CountryListFragment()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)

        tabLayout.addTab(tabLayout.newTab().setText("Map").setTag("Map"))
        tabLayout.addTab(tabLayout.newTab().setText("Top Ten").setTag("Top Ten"))


        enter_your_letter.setOnClickListener {
            EnterLetterFragment().addToNavigationStack(
                    (activity as BottomNavigationActivity).supportFragmentManager,
                    R.id.fragment_container,
                    "enter_letter_fragment")
        }
    }

        manager = childFragmentManager

        transaction = manager?.beginTransaction()
        transaction?.add(R.id.fragment_map_container, mapShownFragment)
        transaction?.commit()

        tabLayout.addOnTabSelectedListener(object : BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                manager?.popBackStack()
                transaction = manager?.beginTransaction()


                // Use hide/show to save the state of fragment
                when (tab?.tag) {
                    "Map" -> {
                        transaction?.hide(countryListFragment)
                        transaction?.show(mapShownFragment)
                        transaction?.commit()
                    }

                    "Top Ten" -> {
                        transaction?.hide(mapShownFragment)
                        if (countryListFragment.isAdded) {
                            transaction?.show(countryListFragment)
                            transaction?.commit()
                        } else {
                            transaction?.add(R.id.fragment_map_container, countryListFragment)
                            transaction?.commit()
                        }

                    }
                }


            }

        })


    }
}
