package com.kidssavetheocean.fatechanger.map


import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
import com.kidssavetheocean.fatechanger.BR
import com.kidssavetheocean.fatechanger.R
import com.kidssavetheocean.fatechanger.databinding.FragmentMapBinding
import com.kidssavetheocean.fatechanger.firebase.FirebaseService
import com.kidssavetheocean.fatechanger.firebase.model.CountryModel
import com.kidssavetheocean.fatechanger.presentation.mvvm.fragment.AbstractFragment
import com.kidssavetheocean.fatechanger.presentation.mvvm.vm.EmptyViewModel
import dagger.hilt.android.AndroidEntryPoint

import java.util.Observable
import java.util.Observer

@AndroidEntryPoint
class MapFragment : AbstractFragment<FragmentMapBinding, EmptyViewModel>(), Observer {
    var manager: FragmentManager? = null
    var transaction: FragmentTransaction? = null
    var mapShownFragment = MapShownFragment()
    var countryListFragment = CountryListFragment()
    var flag = 0

    override fun onPrepareLayout(layoutView: View?) {
        super.onPrepareLayout(layoutView)
        FirebaseService.getInstance().addObserver(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tabLayout = binding.tabLayout

        updateText(FirebaseService.getInstance().countries)

        tabLayout.run {
            addTab(newTab().setText("Map View").setTag("Map"))
            addTab(newTab().setText("Top 10 View").setTag("Top Ten"))
            getTabAt(flag)?.select()
        }

        binding.enterYourLetter.setOnClickListener {
            navigateToView(EnterLetterFragment::class)
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
                        tabLayout.getTabAt(0)?.text = "Map"
                        tabLayout.getTabAt(1)?.text = "Top 10 View"
                        transaction?.run {
                            hide(countryListFragment)
                            show(mapShownFragment)
                            commit()
                        }
                    }

                    "Top Ten" -> {
                        flag = 1
                        tabLayout.getTabAt(0)?.text = "Map View"
                        tabLayout.getTabAt(1)?.text = "Top 10"
                        transaction?.run {

                            hide(mapShownFragment)
                            if (countryListFragment.isAdded) {
                                show(countryListFragment)
                            } else {
                                add(R.id.fragment_map_container, countryListFragment)
                            }
                            commit()
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
            binding.countries.text = data.size.toString()

            for (i in data.indices) {
                letterNum = letterNum.plus(data[i].country_number)
            }

            binding.lettersWritten.text = letterNum.toString()
        }

    }

    override fun onResume() {
        super.onResume()
        manager = childFragmentManager
        transaction = manager?.beginTransaction()?.apply {
            if (!mapShownFragment.isAdded)
                add(R.id.fragment_map_container, mapShownFragment)
            else
                show(mapShownFragment)
            commit()
        }
    }

    override fun getViewModelResId(): Int = BR.emptyVM

    override fun getLayoutResId(): Int = R.layout.fragment_map

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java
}
