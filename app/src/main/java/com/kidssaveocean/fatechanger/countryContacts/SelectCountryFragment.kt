package com.kidssaveocean.fatechanger.countryContacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.firebase.CountryModel
import com.kidssaveocean.fatechanger.firebase.FirebaseService
import java.util.*


class SelectCountryFragment : Fragment(), Observer {

    private val countries : MutableCollection<CountryModel> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.fragment_letter, container, false)

        val countryNames = countries.map { it.countryName }.toTypedArray()
        val picker = view.findViewById(R.id.country_picker) as NumberPicker?
        picker?.minValue = 0
        picker?.maxValue = countries.size - 1
        picker?.displayedValues = countryNames
        picker?.wrapSelectorWheel = true
        picker?.setOnValueChangedListener { picker, oldVal, newVal ->
            Log.d("new value", newVal.toString())
        }

        return view
    }

    override fun update(o: Observable?, arg: Any?) {
        when (o) {
            is FirebaseService -> {
                for (country in o.getCountries()) {
                    countries.add(country)
                }
            }
        }
    }
}
