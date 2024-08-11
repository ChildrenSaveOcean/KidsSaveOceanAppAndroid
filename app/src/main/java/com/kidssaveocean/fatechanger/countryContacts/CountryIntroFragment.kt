package com.kidssaveocean.fatechanger.countryContacts

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.kidssaveocean.fatechanger.BR
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.databinding.FragmentCountryIntroBinding
import com.kidssaveocean.fatechanger.firebase.FirebaseService
import com.kidssaveocean.fatechanger.news.NewsFragment
import com.kidssaveocean.fatechanger.presentation.mvvm.fragment.AbstractFragment
import com.kidssaveocean.fatechanger.presentation.mvvm.vm.EmptyViewModel
import com.kidssaveocean.fatechanger.webview.WebViewFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryIntroFragment : AbstractFragment<FragmentCountryIntroBinding, EmptyViewModel>() {

    override fun onPrepareLayout(layoutView: View?) {
        super.onPrepareLayout(layoutView)
        //TODO this is bad, needs fixing
        val fragment = SelectCountryFragment()
        FirebaseService.getInstance().addObserver(fragment)
        (activity as AppCompatActivity).supportActionBar?.run {
            setHomeButtonEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setHasOptionsMenu(true)
        }

        view?.findViewById<Toolbar>(R.id.toolbar)?.setNavigationOnClickListener {
            (activity as AppCompatActivity).onBackPressed()
        }
        binding.writeToWhereButton.setOnClickListener {
            if (FirebaseService.getInstance().hasCountries) {
                navigateToView(SelectCountryFragment::class)
            }
        }

        binding.writeAboutWhatButton.setOnClickListener {
            val bundle = Bundle().apply {
                putString(WebViewFragment.URL_KEY, WebViewFragment.WEB_VIEW_LETTER_URL)
            }
            navigateToView(NewsFragment::class, bundle)
        }
    }

    override fun getViewModelResId(): Int = BR.emptyVM

    override fun getLayoutResId(): Int = R.layout.fragment_country_intro

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java
}
