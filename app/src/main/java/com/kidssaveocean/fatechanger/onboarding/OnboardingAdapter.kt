package com.kidssaveocean.fatechanger.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

import com.kidssaveocean.fatechanger.R

//todo fix this
class OnboardingAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    //todo fix this adapter
    override fun getItem(position: Int): Fragment {
        val imageId: Int
        val titleId: Int
        val subTitleId: Int
        val descriptionId: Int
        val layoutId: Int
        var extraId: Int

        when (position) {
            0 -> {
                imageId = R.drawable.imagery_welcome
                titleId = R.string.welcome_to_fate_changer
                subTitleId = R.string.children_saving_the_ocean
                descriptionId = R.string.welcome_to_fate_changer_description
                extraId = R.string.are_you_ready
                layoutId = R.layout.fragment_no_button_onboarding
            }
            1 -> {
                imageId = R.drawable.imagery_help
                titleId = R.string.how_you_can_help
                subTitleId = R.string.learn_write_share
                descriptionId = R.string.how_you_can_help_description
                extraId = R.string.empty
                layoutId = R.layout.fragment_no_button_onboarding
            }
            2 -> {
                imageId = R.drawable.imagery_engage
                titleId = R.string.stay_engaged
                subTitleId = R.string.stay_connected_for_updates
                descriptionId = R.string.stay_engaged_description
                extraId = R.string.empty
                layoutId = R.layout.fragment_button_onboarding
            }
            else -> {
                imageId = R.drawable.imagery_welcome
                titleId = R.string.welcome_to_fate_changer
                subTitleId = R.string.children_saving_the_ocean
                descriptionId = R.string.welcome_to_fate_changer_description
                extraId = R.string.are_you_ready
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
        bundle.putInt(OnboardingFragment.EXTRA, extraId)
        return fragment
    }

    override fun getCount() = NUMBER_OF_ONBOARDING_PAGES


    companion object {
        private const val NUMBER_OF_ONBOARDING_PAGES = 3
    }
}
