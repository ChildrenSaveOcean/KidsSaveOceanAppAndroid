package com.kidssaveocean.fatechanger.countryContacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.bottomNavigation.BottomNavigationActivity
import com.kidssaveocean.fatechanger.extensions.addToNavigationStack
import com.kidssaveocean.fatechanger.firebase.CountryModel
import com.kidssaveocean.fatechanger.firebase.FirebaseService
import java.util.*
import kotlin.collections.ArrayList


class SelectCountryFragment : Fragment(), Observer {

    private var countries : ArrayList<CountryModel> = arrayListOf()
    private var letterAddress : String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        if (countries.isEmpty()) {
            countries = FirebaseService.getInstance().getCountries() as ArrayList<CountryModel>
            letterAddress = countries[0]?.countryAddress
        }

        var view = inflater.inflate(R.layout.fragment_letter, container, false)

        val countryNames = countries.map { it.countryName }.toTypedArray()
        val picker = view.findViewById(R.id.country_picker) as NumberPicker?
        picker?.minValue = 0
        picker?.maxValue = countries.size - 1
        picker?.displayedValues = countryNames
        picker?.wrapSelectorWheel = true
        picker?.setOnValueChangedListener { _, _, newVal ->
            val model = countries[newVal]
            letterAddress = model?.countryAddress
        }
        val button = view.findViewById(R.id.submit_button) as Button?
        button?.setOnClickListener {
            var bundle = Bundle()
            bundle.putString("address", letterAddress)
            var fragment = CountryInfoFragment()
            fragment.arguments = bundle
            val bottomActivity = activity as BottomNavigationActivity
            fragment.addToNavigationStack(
                    bottomActivity.supportFragmentManager,
                    R.id.fragment_container,
                    "country_info_fragment")
        }

        return view
    }

    override fun update(o: Observable?, arg: Any?) {
        when (o) {
            is FirebaseService -> {
                countries.clear()
                for (country in o.getCountries()) {
                    countries.add(country)
                }
                letterAddress = countries[0]?.countryAddress
            }
        }
    }
}
