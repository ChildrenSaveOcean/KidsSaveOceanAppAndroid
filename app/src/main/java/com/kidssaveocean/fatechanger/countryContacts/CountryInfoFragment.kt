package com.kidssaveocean.fatechanger.countryContacts

import android.os.Bundle
import android.view.View
import com.kidssaveocean.fatechanger.BR
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.databinding.FragmentCountryInfoBinding
import com.kidssaveocean.fatechanger.presentation.mvvm.fragment.AbstractFragment
import com.kidssaveocean.fatechanger.presentation.mvvm.vm.EmptyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryInfoFragment : AbstractFragment<FragmentCountryInfoBinding, EmptyViewModel>() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val address = arguments?.getString("address") ?: ""
        binding.countryInfoToolbar.toolbar.setNavigationOnClickListener {
            navigateBack()
        }
        binding.adressText.text = address
    }

    override fun getViewModelResId(): Int = BR.emptyVM

    override fun getLayoutResId(): Int = R.layout.fragment_country_info

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java
}