package com.fatechanger.app.countryContacts

import android.os.Bundle
import android.view.View
import com.fatechanger.app.BR
import com.fatechanger.app.R
import com.fatechanger.app.databinding.FragmentCountryInfoBinding
import com.fatechanger.app.presentation.mvvm.fragment.AbstractFragment
import com.fatechanger.app.presentation.mvvm.vm.EmptyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryInfoFragment : AbstractFragment<FragmentCountryInfoBinding, EmptyViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val address = arguments?.getString(ADDRESS_KEY) ?: ""
        binding.countryInfoToolbar.toolbar.setNavigationOnClickListener {
            navigateBack()
        }
        binding.adressText.text = address
    }

    override fun getViewModelResId(): Int = BR.emptyVM

    override fun getLayoutResId(): Int = R.layout.fragment_country_info

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java

    companion object {

        const val ADDRESS_KEY = "address"
    }
}