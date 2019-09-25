package com.kidssaveocean.fatechanger.countryContacts

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.bottomNavigation.BottomNavigationActivity
import com.kidssaveocean.fatechanger.extensions.addToNavigationStack
import com.kidssaveocean.fatechanger.firebase.model.CountryModel
import com.kidssaveocean.fatechanger.firebase.FirebaseService
import java.util.*


class SelectCountryFragment : Fragment(), Observer {

    private val PERMISSION_ACCESS_FINE_LOCATION : Int = 111
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var countries : List<CountryModel> = listOf()
    private var letterAddress : String? = null
    private var picker : NumberPicker? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val bottomActivity = activity as BottomNavigationActivity

        if (countries.isEmpty()) {
            countries = FirebaseService.getInstance().countries
            letterAddress = countries[0]?.country_address
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(bottomActivity)

        val view = inflater.inflate(R.layout.fragment_letter, container, false)

        val countryNames = countries.map { it.country_name }.toTypedArray()
        picker = view.findViewById(R.id.country_picker) as NumberPicker?
        picker?.minValue = 0
        picker?.maxValue = countries.size - 1
        picker?.displayedValues = countryNames
        picker?.wrapSelectorWheel = true
        picker?.setOnValueChangedListener { _, _, newVal ->
            val model = countries[newVal]
            letterAddress = model?.country_address
        }
        val button = view.findViewById(R.id.submit_button) as Button?
        button?.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("address", letterAddress)
            val fragment = CountryInfoFragment()
            fragment.arguments = bundle
            fragment.addToNavigationStack(
                    bottomActivity.supportFragmentManager,
                    R.id.fragment_container,
                    "country_info_fragment")
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomActivity = activity as BottomNavigationActivity
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

    override fun update(o: Observable?, arg: Any?) {
        when (o) {
            is FirebaseService -> {
                countries = o.countries
                letterAddress = countries[0]?.country_address
            }
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

    @SuppressLint("MissingPermission")
    private fun obtainLocation() {
        fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location?.let { it ->
                        val latitude = it.latitude
                        val longitude = it.longitude
                        val country = findCountry(latitude, longitude)
                        country?.let { c ->
                            val index = countries.indexOf(c)
                            picker?.value = index
                        }
                    }
                }
    }

    private fun findCountry(latitude : Double, longitude : Double): CountryModel? {
        var closestCoutry : CountryModel? = null
        var minDistance = 0.0
        val startPoint = Location("myLocation")
        startPoint.latitude = latitude
        startPoint.longitude = longitude
        for (country in countries) {
            val endPoint = Location("capitalLocation")
            endPoint.latitude = country.latitude
            endPoint.longitude = country.longitude
            val distance = startPoint.distanceTo(endPoint).toDouble()
            if (minDistance == 0.0 || distance < minDistance) {
                minDistance = distance
                closestCoutry = country
            }
        }
        return closestCoutry
    }
}
