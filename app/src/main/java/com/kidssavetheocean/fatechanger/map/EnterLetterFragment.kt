package com.kidssavetheocean.fatechanger.map

import android.app.AlertDialog
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.kidssavetheocean.fatechanger.BR
import com.kidssavetheocean.fatechanger.R
import com.kidssavetheocean.fatechanger.bottomNavigation.BottomNavigationActivity
import com.kidssavetheocean.fatechanger.dashboard.DashboardSteps
import com.kidssavetheocean.fatechanger.dashboard.MainDashboardFragment
import com.kidssavetheocean.fatechanger.databinding.FragmentEnterLetterBinding
import com.kidssavetheocean.fatechanger.extensions.getCountryByLocation
import com.kidssavetheocean.fatechanger.firebase.FirebaseService
import com.kidssavetheocean.fatechanger.firebase.model.CountryModel
import com.kidssavetheocean.fatechanger.presentation.mvvm.fragment.AbstractFragment
import com.kidssavetheocean.fatechanger.presentation.mvvm.vm.EmptyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_enter_letter.country_picker
import kotlinx.android.synthetic.main.fragment_enter_letter.progressBar_cyclic
import kotlinx.android.synthetic.main.fragment_enter_letter.submit_button

/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class EnterLetterFragment : AbstractFragment<FragmentEnterLetterBinding, EmptyViewModel>() {

    private val PERMISSION_ACCESS_FINE_LOCATION: Int = 111
    private var countries: List<CountryModel> = listOf()
    private var currentCountry: CountryModel? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onPrepareLayout(layoutView: View?) {
        super.onPrepareLayout(layoutView)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun getViewModelResId(): Int = BR.emptyVM

    override fun getLayoutResId(): Int = R.layout.fragment_enter_letter

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomActivity = activity as BottomNavigationActivity

        countries = FirebaseService.getInstance().countries
        submit_button.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext())
            dialog
                .setTitle(R.string.enter_letter_dialog_question)
                .setPositiveButton(R.string.enter_letter_positive_answer) { _, _ ->
                    run {
                        currentCountry?.let { FirebaseService.getInstance().increaseWrittenLettersNumber(it) }
                    }
                    AlertDialog.Builder(requireContext())
                        .setTitle(R.string.youre_letter_has_been_recorded)
                        .setMessage(R.string.enter_letter_congratulations_title)
                        .setPositiveButton(R.string.fatechangers_click_here) { _, _ ->
                            val bundle = Bundle().apply { putSerializable(MainDashboardFragment.DASHBOARD_STEP_KEY, DashboardSteps.WRITE_LETTER) }
                            navigateToView(MainDashboardFragment::class, bundle)
                        }
                        .create()
                        .show()

                }
                .setNegativeButton(R.string.enter_letter_negative_answer) { d, _ -> d.cancel() }

            dialog.create().show()
        }

        when (countries.isNotEmpty()) {
            true -> fillPicker(countries)
            false -> {
                FirebaseService.getInstance().countriesObservable.observe(this.viewLifecycleOwner) {
                    if (it.isNotEmpty()) {
                        progressBar_cyclic.visibility = View.GONE
                        country_picker.visibility = View.VISIBLE
                        countries = it
                        fillPicker(it)

                        if (ContextCompat.checkSelfPermission(
                                bottomActivity,
                                android.Manifest.permission.ACCESS_FINE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            ActivityCompat.requestPermissions(
                                bottomActivity,
                                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                                PERMISSION_ACCESS_FINE_LOCATION
                            )
                        } else {
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
        country_picker.visibility = View.VISIBLE
        progressBar_cyclic.visibility = View.GONE
        country_picker.setOnValueChangedListener { _, _, newVal ->
            currentCountry = countries[newVal]
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        //todo fix
        when (requestCode) {
            PERMISSION_ACCESS_FINE_LOCATION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    obtainLocation()
                }
            }
        }
    }

    private fun obtainLocation() {
        //todo fix
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
