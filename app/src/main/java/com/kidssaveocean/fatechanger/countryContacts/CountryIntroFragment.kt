package com.kidssaveocean.fatechanger.countryContacts

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.bottomNavigation.BottomNavigationActivity
import com.kidssaveocean.fatechanger.extensions.addToNavigationStack
import com.kidssaveocean.fatechanger.firebase.FirebaseService

class CountryIntroFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val fragment = SelectCountryFragment()
        FirebaseService.getInstance().addObserver(fragment)
        val view = inflater.inflate(R.layout.fragment_country_intro, container, false)
        var button = view.findViewById(R.id.write_to_where_button) as Button?
        button?.setOnClickListener {
            if (FirebaseService.getInstance().hasCountries) {
                val bottomActivity = activity as BottomNavigationActivity
                fragment.addToNavigationStack(
                    bottomActivity.supportFragmentManager,
                    R.id.fragment_container,
                    "select_country_fragment")
            }
        }
        return view
    }
}
