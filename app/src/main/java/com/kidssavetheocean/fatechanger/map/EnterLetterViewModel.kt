package com.kidssavetheocean.fatechanger.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.FirebaseDatabase
import com.kidssavetheocean.fatechanger.firebase.FirebaseService
import com.kidssavetheocean.fatechanger.firebase.FirebaseService.Companion.COUNTRIES_TABLE
import com.kidssavetheocean.fatechanger.firebase.model.CountryModel
import com.kidssavetheocean.fatechanger.presentation.AbstractViewModel
import com.kidssavetheocean.fatechanger.service.coroutines.ICoroutineContextProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnterLetterViewModel @Inject constructor(
    contextProvider: ICoroutineContextProvider,
    private val firebaseService: FirebaseService
) : AbstractViewModel(contextProvider) {

    private val _screenState = MutableLiveData<CountriesState>()

    val screenState: LiveData<CountriesState>
        get() = _screenState

    private val _countries: LiveData<List<CountryModel>>
        get() = firebaseService.countries
//    private val _countries = MediatorLiveData<List<CountryModel>>().apply {
//        addSource(firebaseService.countries) {
//            _screenState.postValue(DataLoaded(it))
//        }
//    }

    fun addLetterToDatabase(countryIndex: Int) {
        val countries = _countries.value
        if(countryIndex < 0 || countries.isNullOrEmpty() || countryIndex >= countries.size)
            return
        _screenState.value = LoadingData

        backgroundScope.launch {
            val currentCountry = countries[countryIndex]
            currentCountry.let { country ->
                val writtenLetters = country.letters_written_to_country + 1

                val dbObject: HashMap<String, Any> = hashMapOf(
                    "country_name" to country.country_name,
                    "country_number" to country.country_number,
                    "country_address" to country.country_address,
                    "country_head_of_state_title" to country.country_head_of_state_title,
                    "latitude" to country.latitude,
                    "longitude" to country.longitude,
                    "letters_written_to_country" to writtenLetters
                )

                FirebaseDatabase
                    .getInstance()
                    .reference
                    .child(COUNTRIES_TABLE)
                    .child(country.country_code)
                    .setValue(dbObject)
                    .addOnSuccessListener {
                        _screenState.postValue(LetterSent)
                    }
                    .addOnFailureListener {
                        _screenState.postValue(SendingFailed)
                    }
            }
        }
    }

    fun loadCountriesData() {
        backgroundScope.launch {
            firebaseService.getCountriesData()
        }
    }
}

sealed class CountriesState
object LoadingData : CountriesState()
class DataLoaded(val data: List<CountryModel>) : CountriesState()
object LetterSent : CountriesState()
object SendingFailed: CountriesState()
