package com.kidssaveocean.fatechanger.map


import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.transition.Visibility
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.bottomNavigation.BottomNavigationActivity
import com.kidssaveocean.fatechanger.extensions.getCountryByLocation
import com.kidssaveocean.fatechanger.firebase.FirebaseService
import com.kidssaveocean.fatechanger.firebase.model.CountryModel
import kotlinx.android.synthetic.main.fragment_enter_letter.*

/**
 * A simple [Fragment] subclass.
 */
class EnterLetterFragment : Fragment() {

    private val PERMISSION_ACCESS_FINE_LOCATION : Int = 111
    private var countries : List<CountryModel> = listOf()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val bottomActivity = activity as BottomNavigationActivity
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(bottomActivity)
        return inflater.inflate(R.layout.fragment_enter_letter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomActivity = activity as BottomNavigationActivity

        countries = FirebaseService.getInstance().countries

        when (countries.isNotEmpty()) {
            true -> fillPicker(countries)
            false -> {
                FirebaseService.getInstance().countriesObservable.subscribe {
                    if (it.isNotEmpty()) {
                        progressBar_cyclic.visibility = View.GONE
                        country_picker.visibility = View.VISIBLE
                        countries = it
                        fillPicker(it)

                        if (ContextCompat.checkSelfPermission(bottomActivity, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
                            ActivityCompat.requestPermissions(
                                    bottomActivity,
                                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                                    PERMISSION_ACCESS_FINE_LOCATION
                            )
                        }
                        else {
                            obtainLocation()
                        }
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_ACCESS_FINE_LOCATION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    obtainLocation()
                }
            }
        }
    }

    private fun obtainLocation() {
        fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location?.let { it ->
                        val country = it.getCountryByLocation(countries)
                        country?.let { c ->
                            val index = countries.indexOf(c)
                            country_picker?.value = index
                        }
                    }
                }
    }
}
