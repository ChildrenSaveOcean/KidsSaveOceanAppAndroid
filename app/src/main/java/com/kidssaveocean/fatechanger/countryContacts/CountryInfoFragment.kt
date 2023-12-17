package com.kidssaveocean.fatechanger.countryContacts

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.kidssaveocean.fatechanger.BR
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.databinding.FragmentCountryInfoBinding
import com.kidssaveocean.fatechanger.presentation.mvvm.fragment.AbstractFragment
import com.kidssaveocean.fatechanger.presentation.mvvm.vm.EmptyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryInfoFragment : AbstractFragment<FragmentCountryInfoBinding, EmptyViewModel>() {

    private var address: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            address = it.getString("address")
        }

    }

    override fun onPrepareLayout(layoutView: View?) {
        super.onPrepareLayout(layoutView)
        (activity as AppCompatActivity).supportActionBar?.run{
            setHomeButtonEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setHasOptionsMenu(true)
        }

        layoutView?.findViewById<Toolbar>(R.id.toolbar)?.setNavigationOnClickListener {
            //todo what ????
            (activity as AppCompatActivity).onBackPressed()
        }
        val textView = layoutView?.findViewById(R.id.adress_text) as TextView
        textView.text = address

    }

    override fun getViewModelResId(): Int = BR.emptyVM

    override fun getLayoutResId(): Int = R.layout.fragment_country_info

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java
}