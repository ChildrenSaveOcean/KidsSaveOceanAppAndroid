package com.fatechanger.app.onboarding.userIdentification

import android.content.Intent
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.fatechanger.app.BR
import com.fatechanger.app.R
import com.fatechanger.app.databinding.FragmentUserIdentificationCardBinding
import com.fatechanger.app.presentation.mvvm.fragment.AbstractFragment
import com.fatechanger.app.presentation.mvvm.vm.EmptyViewModel
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.fragment_user_identification_card.description
import kotlinx.android.synthetic.main.fragment_user_identification_card.user_identification_image_button
import kotlinx.android.synthetic.main.fragment_user_identification_card.user_identification_type_text_view

//todo this and UserIdentificationFragment might be only one fragment

@AndroidEntryPoint
class UserIdentificationCardFragment : AbstractFragment<FragmentUserIdentificationCardBinding, EmptyViewModel>() {

    override fun getViewModelResId(): Int = BR.emptyVM

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java

    override fun getLayoutResId(): Int = R.layout.fragment_user_identification_card

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