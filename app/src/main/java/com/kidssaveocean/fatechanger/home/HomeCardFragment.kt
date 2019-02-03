package com.kidssaveocean.fatechanger.home

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

import kotlinx.android.synthetic.main.fragment_home_card.*
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class HomeCardFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? =
            inflater.inflate(R.layout.fragment_home_card, container, false)

    fun setBackgroundImage(backgroundImageId: Int) {
        val multiTransformation = MultiTransformation(
                CenterCrop(),
                RoundedCornersTransformation(50, 0)
        )

        Glide.with(this)
             .load(backgroundImageId)
             .apply(RequestOptions.bitmapTransform(multiTransformation))
             .into(home_card_image_button)
    }

    fun setTypeText(textId: Int) = type_text_view.setText(textId)


    fun setTitleText(textId: Int) = title_text_view.setText(textId)


    fun setSubtitleTextView(textId: Int) = subtitle_text_view.setText(textId)
}
