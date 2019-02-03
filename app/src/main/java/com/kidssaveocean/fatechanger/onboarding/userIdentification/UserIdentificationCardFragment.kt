package com.kidssaveocean.fatechanger.onboarding.userIdentification

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.kidssaveocean.fatechanger.R

import kotlinx.android.synthetic.main.fragment_user_identification_card.*
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class UserIdentificationCardFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_user_identification_card, container, false)

    fun setBackgroundImage(backgroundImageId: Int) {
        val multiTransformation = MultiTransformation(
                CenterCrop(),
                RoundedCornersTransformation(50, 0)
        )

        Glide.with(this)
             .load(backgroundImageId)
             .apply(RequestOptions.bitmapTransform(multiTransformation))
             .into(user_identification_image_button)
    }

    fun setUserTypeText(textId: Int) = user_identification_type_text_view.setText(textId)

    fun setDesciption(textId: Int) = description.setText(textId)

    fun setUpImageButtonOnClick(type: String) {
        user_identification_image_button.setOnClickListener { v ->
            val intent = Intent(activity, IntroductionVideoActivity::class.java)
            intent.putExtra(IntroductionVideoActivity.INTRO_TYPE, type)
            startActivity(intent)
        }
    }

}