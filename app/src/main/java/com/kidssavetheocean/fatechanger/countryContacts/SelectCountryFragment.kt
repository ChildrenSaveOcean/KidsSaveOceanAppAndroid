package com.kidssavetheocean.fatechanger.countryContacts

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.kidssavetheocean.fatechanger.BR
import com.kidssavetheocean.fatechanger.R
import com.kidssavetheocean.fatechanger.bottomNavigation.BottomNavigationActivity
import com.kidssavetheocean.fatechanger.databinding.FragmentLetterBinding
import com.kidssavetheocean.fatechanger.extensions.getCountryByLocation
import com.kidssavetheocean.fatechanger.firebase.FirebaseService
import com.kidssavetheocean.fatechanger.firebase.model.CountryModel
import com.kidssavetheocean.fatechanger.presentation.mvvm.fragment.AbstractFragment
import com.kidssavetheocean.fatechanger.presentation.mvvm.vm.EmptyViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Observable
import java.util.Observer

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
            bundle.putString(CountryInfoFragment.ADDRESS_KEY, letterAddress)
            navigateToView(CountryInfoFragment::class, bundle)
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

//    //todo fix this
    override fun update(o: Observable?, arg: Any?) {
        when (o) {
            is FirebaseService -> {
                countries = o.countries
                letterAddress = countries[0].country_address
            }
        }
    }

    //todo update this
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_ACCESS_FINE_LOCATION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    obtainLocation()
                }
            }
        }
    }

    //todo fix this method and everything with locaiton
    @SuppressLint("MissingPermission")
    private fun obtainLocation() {
        val country: String = requireContext().resources.configuration.locales[0].country
        Log.e("test", "country of mine: $country")
        val sb = StringBuilder()
        countries.forEach {
            sb.append(it.country_name).append("--\t--")
        }
        Log.e("test", sb.toString())
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
