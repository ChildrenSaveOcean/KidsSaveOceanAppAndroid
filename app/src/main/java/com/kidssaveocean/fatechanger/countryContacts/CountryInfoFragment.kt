package com.kidssaveocean.fatechanger.countryContacts

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.bottomNavigation.BottomNavigationActivity

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CountryInfoFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CountryInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class CountryInfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_country_info, container, false)
        var button = view.findViewById(R.id.write_to_where_button) as Button?
        button?.setOnClickListener {
            val bottomActivity = activity as BottomNavigationActivity
            val fragmentTransaction = bottomActivity.supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, SelectCountryFragment(), "select_country_fragment")
            fragmentTransaction.addToBackStack("select_country_fragment")
            fragmentTransaction.commit()
        }
        return view
    }
}