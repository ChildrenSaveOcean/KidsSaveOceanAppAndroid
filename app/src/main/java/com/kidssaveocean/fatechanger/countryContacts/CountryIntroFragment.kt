package com.kidssaveocean.fatechanger.countryContacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.bottomNavigation.BottomNavigationActivity
import com.kidssaveocean.fatechanger.extensions.addToNavigationStack
import com.kidssaveocean.fatechanger.firebase.FirebaseService


class CountryIntroFragment : Fragment() {
    companion object {
        const val WRITE_ABOUT_WAHT = "https://pederhill.wixsite.com/kids-save-ocean/copy-of-write-letters-with-your-kid"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val fragment = SelectCountryFragment()
        FirebaseService.getInstance().addObserver(fragment)
        val view = inflater.inflate(R.layout.fragment_country_intro, container, false)
        (activity as AppCompatActivity).supportActionBar?.run{
            setHomeButtonEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setHasOptionsMenu(true)
        }

        view.findViewById<Toolbar>(R.id.toolbar).setNavigationOnClickListener {
            (activity as AppCompatActivity).onBackPressed()
        }
        var button = view.findViewById(R.id.write_to_where_button) as Button?
        var btnWriteWhat = view.findViewById(R.id.write_about_what_button) as Button?
        button?.setOnClickListener {
            if (FirebaseService.getInstance().hasCountries) {
                val bottomActivity = activity as BottomNavigationActivity
                fragment.addToNavigationStack(
                    bottomActivity.supportFragmentManager,
                    R.id.fragment_container,
                    "select_country_fragment")
            }
        }

        btnWriteWhat?.setOnClickListener {
            val bottomActivity = activity as BottomNavigationActivity
            WebViewFragment().addToNavigationStack(
                    bottomActivity.supportFragmentManager,
                    R.id.fragment_container,
                    "select_country_fragment")
        }
        return view
    }
}
