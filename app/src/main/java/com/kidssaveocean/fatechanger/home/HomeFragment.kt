package com.kidssaveocean.fatechanger.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kidssaveocean.fatechanger.countryContacts.SelectCountryFragment

import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.bottomNavigation.BottomNavigationActivity
import kotlinx.android.synthetic.main.activity_bottom_navigation.*


class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val letterFragment = childFragmentManager.findFragmentById(R.id.letter_fragment) as HomeCardFragment?
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as HomeCardFragment?
        val qaFragment = childFragmentManager.findFragmentById(R.id.qa_fragment) as HomeCardFragment?
        val scoreFragment = childFragmentManager.findFragmentById(R.id.score_fragment) as HomeCardFragment?
        val dashboardFragment = childFragmentManager.findFragmentById(R.id.dashboard_fragment) as HomeCardFragment?
        val bottomActivity = activity as BottomNavigationActivity

        letterFragment?.setBackgroundImage(R.drawable.sunset_and_people)
        letterFragment?.setTypeText(R.string.how_you_can_help_capitalized)
        letterFragment?.setTitleText(R.string.write_and_get_the_world_out)
        letterFragment?.setSubtitleTextView(R.string.empty_string)
        letterFragment?.tapAction {
            val fragmentTransaction = bottomActivity.supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, SelectCountryFragment(), "letter_fragment")
            fragmentTransaction.addToBackStack("letter_fragment")
            fragmentTransaction.commit()
        }

        mapFragment?.setBackgroundImage(R.drawable.letter_writing_map)
        mapFragment?.setTypeText(R.string.updates)
        mapFragment?.setTitleText(R.string.letter_writing_update)
        mapFragment?.setSubtitleTextView(R.string.see_our_progress)

        qaFragment?.setBackgroundImage(R.drawable.surfer)
        qaFragment?.setTypeText(R.string.interview)
        qaFragment?.setTitleText(R.string.peder_hill)
        qaFragment?.setSubtitleTextView(R.string.q_and_a_with_founder)

        scoreFragment?.setBackgroundImage(R.drawable.dolphins)
        scoreFragment?.setTypeText(R.string.updates)
        scoreFragment?.setTitleText(R.string.high_scores)
        scoreFragment?.setSubtitleTextView(R.string.see_where_your_country_ranks)

        dashboardFragment?.setBackgroundImage(R.drawable.dashboard)
        dashboardFragment?.setTypeText(R.string.light_it_up)
        dashboardFragment?.setTitleText(R.string.your_activist_dashboard)
        dashboardFragment?.setSubtitleTextView(R.string.empty_string)
        dashboardFragment?.tapAction {
            bottomActivity.bottom_navigation_bar?.selectedItemId = R.id.action_dashboard
        }

        return view
    }

}
