package com.kidssaveocean.fatechanger.map


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.bottomNavigation.BottomNavigationActivity
import com.kidssaveocean.fatechanger.firebase.FirebaseService
import com.kidssaveocean.fatechanger.firebase.model.CountryModel
import kotlinx.android.synthetic.main.fragment_enter_letter.*

/**
 * A simple [Fragment] subclass.
 */
class EnterLetterFragment : Fragment() {

    private var countries : List<CountryModel> = listOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enter_letter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        countries = FirebaseService.getInstance().countries

        when (countries.isNotEmpty()) {
            true -> fillPicker(countries)
            false -> {
                FirebaseService.getInstance().countriesObservable.subscribe {
                    if (it.isNotEmpty()) {
                        countries = it
                        fillPicker(it)
                    }
                }
            }
        }
    }

    private fun fillPicker(countries: List<CountryModel>) {
        val countryNames = countries.map { it.country_name }.toTypedArray()
        country_picker.minValue = 0
        country_picker.maxValue = countries.size - 1
        country_picker.displayedValues = countryNames
        country_picker.wrapSelectorWheel = true
        country_picker.setOnValueChangedListener { _, _, newVal ->
            val model = countries[newVal]
        }
    }
}
