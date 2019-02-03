package com.kidssaveocean.fatechanger.bottomNavigation

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

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

            val transaction = supportFragmentManager.beginTransaction()
            val fragment: Fragment

            when (menuItem.itemId) {
                R.id.action_home -> {
                    fragment = HomeFragment()
                    transaction.replace(R.id.fragment_container, fragment)
                }

                R.id.action_news -> {
                    fragment = NewsFragment()
                    transaction.replace(R.id.fragment_container, fragment)
                }

                R.id.action_donate -> {
                    fragment = DonationFragment()
                    transaction.replace(R.id.fragment_container, fragment)
                }

                R.id.action_resources -> {
                    fragment = ResourcesFragment()
                    transaction.replace(R.id.fragment_container, fragment)
                }

                R.id.action_map -> {
                    fragment = MapFragment()
                    transaction.replace(R.id.fragment_container, fragment)
                }
            }

            transaction.commit()

            true
        }
    }

}
