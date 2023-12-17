package com.kidssaveocean.fatechanger.countryContacts

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.kidssaveocean.fatechanger.BR
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.bottomNavigation.BottomNavigationActivity
import com.kidssaveocean.fatechanger.databinding.FragmentLetterBinding
import com.kidssaveocean.fatechanger.extensions.addToNavigationStack
import com.kidssaveocean.fatechanger.extensions.getCountryByLocation
import com.kidssaveocean.fatechanger.firebase.FirebaseService
import com.kidssaveocean.fatechanger.firebase.model.CountryModel
import com.kidssaveocean.fatechanger.presentation.mvvm.fragment.AbstractFragment
import com.kidssaveocean.fatechanger.presentation.mvvm.vm.EmptyViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

//todo a lot of stuff to fix here
@AndroidEntryPoint
class SelectCountryFragment : AbstractFragment<FragmentLetterBinding, EmptyViewModel>(), Observer {

    private val PERMISSION_ACCESS_FINE_LOCATION : Int = 111
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var countries : List<CountryModel> = listOf()
    private var letterAddress : String? = null

    override fun onPrepareLayout(layoutView: View?) {
        super.onPrepareLayout(layoutView)
        val bottomActivity = activity as BottomNavigationActivity

        //todo fix this countries
        if (countries.isEmpty()) {
            countries = FirebaseService.getInstance().countries
            letterAddress = countries[0].country_address
        }

        //todo get rid of those fused location clients
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(bottomActivity)


        val countryNames = countries.map { it.country_name }.toTypedArray()
        binding.countryPicker.apply {
            minValue = 0
            maxValue = countries.size - 1
            displayedValues = countryNames
            wrapSelectorWheel = true
            setOnValueChangedListener { _, _, newVal ->
                val model = countries[newVal]
                letterAddress = model.country_address
            }
        }

        binding.submitButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("address", letterAddress)
            val fragment = CountryInfoFragment()
            fragment.arguments = bundle
            fragment.addToNavigationStack(
                bottomActivity.supportFragmentManager,
                R.id.fragment_container,
                "country_info_fragment")
        }

    }

    override fun getViewModelResId(): Int = BR.emptyVM

    override fun getLayoutResId(): Int  = R.layout.fragment_letter

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java

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
                letterAddress = countries[0].country_address
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
                        val country = it.getCountryByLocation(countries)
                        country?.let { c ->
                            val index = countries.indexOf(c)
                            binding.countryPicker.value = index
                        }
                    }
                }
    }
}
