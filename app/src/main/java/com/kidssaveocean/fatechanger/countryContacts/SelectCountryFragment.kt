package com.kidssaveocean.fatechanger.countryContacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import com.kidssaveocean.fatechanger.R

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SelectCountryFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SelectCountryFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SelectCountryFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.fragment_letter, container, false)

        val picker = view.findViewById(R.id.country_picker) as NumberPicker?
        val countries = arrayOf("country_0", "country_1", "country_2", "country_3", "country_4")
        picker?.minValue = 0
        picker?.maxValue = countries.size - 1
        picker?.displayedValues = countries
        picker?.wrapSelectorWheel = true
        picker?.setOnValueChangedListener { picker, oldVal, newVal ->
            Log.d("new value", newVal.toString())
        }

        return view
    }
}
