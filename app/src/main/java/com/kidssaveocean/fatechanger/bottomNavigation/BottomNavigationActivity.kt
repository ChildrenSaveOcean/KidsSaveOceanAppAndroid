package com.kidssaveocean.fatechanger.bottomNavigation

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity

import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.donation.DonationFragment
import com.kidssaveocean.fatechanger.home.HomeFragment
import com.kidssaveocean.fatechanger.map.MapFragment
import com.kidssaveocean.fatechanger.news.NewsFragment
import com.kidssaveocean.fatechanger.resources.ResourcesFragment

import kotlinx.android.synthetic.main.activity_bottom_navigation.*


class BottomNavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation)

        BottomNavigationViewHelper.removeShiftMode(bottom_navigation_bar)
        bottom_navigation_bar.setOnNavigationItemSelectedListener(setUpOnNavigationItemSelectedListener())

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, HomeFragment())
        transaction.commit()
    }

    private fun setUpOnNavigationItemSelectedListener(): BottomNavigationView.OnNavigationItemSelectedListener {

        return BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->

            val fragment: Fragment? = when (menuItem.itemId) {
                R.id.action_home -> HomeFragment()
                R.id.action_news -> NewsFragment()
                R.id.action_dashboard -> ResourcesFragment()
                R.id.action_resources -> ResourcesFragment()
                R.id.action_map -> MapFragment()
                else -> null
            }

            fragment?.run {
                supportFragmentManager.popBackStack()
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, this)
                transaction.commit()
                true
            } ?: false
        }
    }
}
