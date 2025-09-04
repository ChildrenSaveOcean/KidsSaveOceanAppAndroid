package com.kidssavetheocean.fatechanger.map

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.kidssavetheocean.fatechanger.BR
import com.kidssavetheocean.fatechanger.R
import com.kidssavetheocean.fatechanger.dashboard.DashboardSteps
import com.kidssavetheocean.fatechanger.dashboard.MainDashboardFragment
import com.kidssavetheocean.fatechanger.databinding.FragmentEnterLetterBinding
import com.kidssavetheocean.fatechanger.extensions.getCountryByLocation
import com.kidssavetheocean.fatechanger.firebase.FirebaseService
import com.kidssavetheocean.fatechanger.firebase.model.CountryModel
import com.kidssavetheocean.fatechanger.presentation.mvvm.fragment.AbstractFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class EnterLetterFragment : AbstractFragment<FragmentEnterLetterBinding, EnterLetterViewModel>() {

    private var currentCountryIndex: Int = -1
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @Inject
    lateinit var firebaseService: FirebaseService

    private val locationActivityLauncher = registerForActivityResult(RequestPermission()) { isGranted ->
        if (isGranted) {
            viewModel.loadCountriesData()
        }
    }

    override fun onPrepareLayout(layoutView: View?) {
        super.onPrepareLayout(layoutView)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun getViewModelResId(): Int = BR.letterVM

    override fun getLayoutResId(): Int = R.layout.fragment_enter_letter

    override fun getViewModelClass(): Class<EnterLetterViewModel> = EnterLetterViewModel::class.java


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submitButton.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext())

            dialog
                .setTitle(R.string.enter_letter_dialog_question)
                .setPositiveButton(R.string.enter_letter_positive_answer) { _, _ ->
                    viewModel.addLetterToDatabase(currentCountryIndex)
                }
                .setNegativeButton(R.string.enter_letter_negative_answer) { d, _ -> d.cancel() }

            dialog.create().show()
        }
        updateState(LoadingData)
        viewModel.screenState.observe(this.viewLifecycleOwner) {
            updateState(it)
        }
        firebaseService.countries.observe(this.viewLifecycleOwner) {
            updateState(DataLoaded(it))
            obtainLocation(it)
        }
        viewModel.loadCountriesData()
    }

    private fun updateState(state: CountriesState) {
        when (state) {
            is DataLoaded -> {
                fillPicker(state.data)
                binding.progressBarCyclic.visibility = View.GONE
                binding.countryPicker.visibility = View.VISIBLE
            }

            LetterSent -> {
                binding.progressBarCyclic.visibility = View.GONE
                binding.countryPicker.visibility = View.VISIBLE
                AlertDialog.Builder(requireContext())
                    .setTitle(R.string.youre_letter_has_been_recorded)
                    .setMessage(R.string.enter_letter_congratulations_title)
                    .setPositiveButton(R.string.fatechangers_click_here) { _, _ ->
                        val bundle = Bundle().apply {
                            putSerializable(
                                MainDashboardFragment.DASHBOARD_STEP_KEY,
                                DashboardSteps.WRITE_LETTER
                            )
                        }
                        navigateToView(MainDashboardFragment::class, bundle)
                    }
                    .create()
                    .show()
            }

            LoadingData -> {
                binding.progressBarCyclic.visibility = View.VISIBLE
                binding.countryPicker.visibility = View.GONE
            }

            SendingFailed -> {
                binding.progressBarCyclic.visibility = View.GONE
                binding.countryPicker.visibility = View.VISIBLE
            }
        }
    }

    private fun fillPicker(countries: List<CountryModel>) {
        val countryNames = countries.map { it.country_name }.toTypedArray()
       binding.countryPicker.minValue = 0
       binding.countryPicker.maxValue = countries.size - 1
       binding.countryPicker.displayedValues = countryNames
       binding.countryPicker.wrapSelectorWheel = true
       binding.countryPicker.visibility = View.VISIBLE
       binding.progressBarCyclic.visibility = View.GONE
       binding.countryPicker.setOnValueChangedListener { _, _, newVal ->
            currentCountryIndex = newVal
        }
    }

    private fun obtainLocation(data: List<CountryModel>) {
        //todo this is "working", but we are making a second unnecessary call for the countries fix it
        val locationPermission =
            checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            locationActivityLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                        val country = location.getCountryByLocation(data)
                    country.let { c ->
                        val index = data.indexOf(c)
                        binding.countryPicker.value = index
                    }
                }
        }
    }
}
