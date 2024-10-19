package com.kidssavetheocean.fatechanger.donation


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.kidssavetheocean.fatechanger.BR

import com.kidssavetheocean.fatechanger.R
import com.kidssavetheocean.fatechanger.databinding.FragmentDonationBinding
import com.kidssavetheocean.fatechanger.presentation.mvvm.fragment.AbstractFragment
import com.kidssavetheocean.fatechanger.presentation.mvvm.vm.EmptyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DonationFragment : AbstractFragment<FragmentDonationBinding, EmptyViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view  = inflater.inflate(R.layout.fragment_donation, container, false)
        val donateButton = view.findViewById<Button>(R.id.donate_button)
        donateButton.setOnClickListener {v ->
            val uri = Uri.parse("https://www.paypal.com")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        return view
    }

    override fun getViewModelResId(): Int = BR.emptyVM

    override fun getLayoutResId(): Int = R.layout.fragment_donation

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java
}
