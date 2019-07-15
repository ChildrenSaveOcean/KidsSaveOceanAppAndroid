package com.kidssaveocean.fatechanger.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.kidssaveocean.fatechanger.R

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val firstFragment = childFragmentManager.findFragmentById(R.id.first_fragment) as HomeCardFragment?
        val secondFragment = childFragmentManager.findFragmentById(R.id.second_fragment) as HomeCardFragment?
        val thirdFragment = childFragmentManager.findFragmentById(R.id.third_fragment) as HomeCardFragment?
        val fourthFragment = childFragmentManager.findFragmentById(R.id.fourth_fragment) as HomeCardFragment?

        firstFragment?.setBackgroundImage(R.drawable.letter_writing_map)
        firstFragment?.setTypeText(R.string.updates)
        firstFragment?.setTitleText(R.string.letter_writing_update)
        firstFragment?.setSubtitleTextView(R.string.see_our_progress)

        secondFragment?.setBackgroundImage(R.drawable.surfer)
        secondFragment?.setTypeText(R.string.interview)
        secondFragment?.setTitleText(R.string.peder_hill)
        secondFragment?.setSubtitleTextView(R.string.q_and_a_with_founder)

        thirdFragment?.setBackgroundImage(R.drawable.dolphins)
        thirdFragment?.setTypeText(R.string.updates)
        thirdFragment?.setTitleText(R.string.high_scores)
        thirdFragment?.setSubtitleTextView(R.string.see_where_your_country_ranks)

        fourthFragment?.setBackgroundImage(R.drawable.dashboard)
        fourthFragment?.setTypeText(R.string.light_it_up)
        fourthFragment?.setTitleText(R.string.your_activist_dashboard)
        fourthFragment?.setSubtitleTextView(R.string.empty_string)

        return view
    }

}
