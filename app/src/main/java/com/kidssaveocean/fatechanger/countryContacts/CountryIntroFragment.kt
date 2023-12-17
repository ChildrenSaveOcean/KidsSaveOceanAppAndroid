package com.kidssaveocean.fatechanger.countryContacts

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.kidssaveocean.fatechanger.BR
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.bottomNavigation.BottomNavigationActivity
import com.kidssaveocean.fatechanger.databinding.FragmentCountryIntroBinding
import com.kidssaveocean.fatechanger.extensions.addToNavigationStack
import com.kidssaveocean.fatechanger.firebase.FirebaseService
import com.kidssaveocean.fatechanger.presentation.mvvm.fragment.AbstractFragment
import com.kidssaveocean.fatechanger.presentation.mvvm.vm.EmptyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryIntroFragment : AbstractFragment<FragmentCountryIntroBinding, EmptyViewModel>() {
    companion object {
        const val WRITE_ABOUT_WAHT = "https://pederhill.wixsite.com/kids-save-ocean/copy-of-write-letters-with-your-kid"
    }

    override fun onPrepareLayout(layoutView: View?) {
        super.onPrepareLayout(layoutView)
        //TODO this is bad, needs fixing
        val fragment = SelectCountryFragment()
        FirebaseService.getInstance().addObserver(fragment)
        (activity as AppCompatActivity).supportActionBar?.run{
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
                val bottomActivity = activity as BottomNavigationActivity
                fragment.addToNavigationStack(
                    bottomActivity.supportFragmentManager,
                    R.id.fragment_container,
                    "select_country_fragment")
            }
        }

        binding.writeAboutWhatButton.setOnClickListener {
            val bottomActivity = activity as BottomNavigationActivity
            WebViewFragment().addToNavigationStack(
                bottomActivity.supportFragmentManager,
                R.id.fragment_container,
                "select_country_fragment")
        }
    }

    override fun getViewModelResId(): Int = BR.emptyVM

    override fun getLayoutResId(): Int = R.layout.fragment_country_intro

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java
}
