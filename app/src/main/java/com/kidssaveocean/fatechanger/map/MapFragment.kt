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
import com.kidssaveocean.fatechanger.firebase.FirebaseService
import com.kidssaveocean.fatechanger.firebase.model.CountryModel
import kotlinx.android.synthetic.main.fragment_map.*
import java.util.*


class MapFragment : Fragment(), Observer {
    var manager: FragmentManager? = null
    var transaction: FragmentTransaction? = null
    var mapShownFragment = MapShownFragment()
    var countryListFragment = CountryListFragment()
    var flag = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        FirebaseService.getInstance().addObserver(this)
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)

        updateText(FirebaseService.getInstance().countries)

        tabLayout.run {
            addTab(newTab().setText("Map View").setTag("Map"))
            addTab(newTab().setText("Top 10 View").setTag("Top Ten"))
            getTabAt(flag)?.select()
        }

        enter_your_letter.setOnClickListener {
            EnterLetterFragment().addToNavigationStack(
                    (activity as BottomNavigationActivity).supportFragmentManager,
                    R.id.fragment_container,
                    "enter_letter_fragment")
        }

        manager = childFragmentManager
        transaction = manager?.beginTransaction()?.apply {
            if (!mapShownFragment.isAdded)
                add(R.id.fragment_map_container, mapShownFragment)
            commit()
        }

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
                        flag = 0
                        transaction?.run {
                            hide(countryListFragment)
                            show(mapShownFragment)
                            commit()
                        }
                    }

                    "Top Ten" -> {
                        flag = 1
                        transaction?.run {

                            hide(mapShownFragment)
                            if (countryListFragment.isAdded) {
                                show(countryListFragment)
                                commit()
                            } else {
                                add(R.id.fragment_map_container, countryListFragment)
                                commit()
                            }
                        }
                    }
                }


            }

        })

    }

    override fun update(o: Observable?, arg: Any?) {
        when (o) {
            is FirebaseService -> {
                updateText(o.countries)
            }
        }
    }

    private fun updateText(data: List<CountryModel>) {
        var letterNum: Long = 0
        if (data.isNotEmpty()) {
            countries.text = data.size.toString()

            for (i in data.indices) {
                letterNum = letterNum.plus(data[i].country_number)
            }

            letters_written.text = letterNum.toString()
        }

    }
}
