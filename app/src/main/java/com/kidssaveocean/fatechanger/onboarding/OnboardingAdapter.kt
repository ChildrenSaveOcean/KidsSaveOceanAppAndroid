package com.kidssaveocean.fatechanger.onboarding

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import com.kidssaveocean.fatechanger.R

class OnboardingAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        val imageId: Int
        val titleId: Int
        val subTitleId: Int
        val descriptionId: Int
        val layoutId: Int

        when (position) {
            0 -> {
                imageId = R.drawable.whale_tail
                titleId = R.string.welcome_to_fate_changer
                subTitleId = R.string.children_saving_the_ocean
                descriptionId = R.string.welcome_to_fate_changer_description
                layoutId = R.layout.fragment_no_button_onboarding
            }
            1 -> {
                imageId = R.drawable.actinia
                titleId = R.string.how_you_can_help
                subTitleId = R.string.learn_write_share
                descriptionId = R.string.how_you_can_help_description
                layoutId = R.layout.fragment_no_button_onboarding
            }
            2 -> {
                imageId = R.drawable.reef
                titleId = R.string.stay_engaged
                subTitleId = R.string.stay_connected_for_updates
                descriptionId = R.string.stay_engaged_description
                layoutId = R.layout.fragment_button_onboarding
            }
            else -> {
                imageId = R.drawable.whale_tail
                titleId = R.string.welcome_to_fate_changer
                subTitleId = R.string.children_saving_the_ocean
                descriptionId = R.string.welcome_to_fate_changer_description
                layoutId = R.layout.fragment_no_button_onboarding
            }
        }

        val bundle = Bundle()
        val fragment = OnboardingFragment()
        fragment.arguments = bundle
        bundle.putInt(OnboardingFragment.IMAGE, imageId)
        bundle.putInt(OnboardingFragment.TITLE, titleId)
        bundle.putInt(OnboardingFragment.SUBTITLE, subTitleId)
        bundle.putInt(OnboardingFragment.DESCRIPTION, descriptionId)
        bundle.putInt(OnboardingFragment.LAYOUT, layoutId)
        return fragment
    }

    override fun getCount() = NUMBER_OF_ONBOARDING_PAGES


    companion object {
        private const val NUMBER_OF_ONBOARDING_PAGES = 3
    }
}
