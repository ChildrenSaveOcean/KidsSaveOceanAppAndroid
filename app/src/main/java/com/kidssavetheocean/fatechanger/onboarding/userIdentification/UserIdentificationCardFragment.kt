package com.kidssavetheocean.fatechanger.onboarding.userIdentification

import android.content.Intent
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.kidssavetheocean.fatechanger.BR
import com.kidssavetheocean.fatechanger.R
import com.kidssavetheocean.fatechanger.databinding.FragmentUserIdentificationCardBinding
import com.kidssavetheocean.fatechanger.presentation.mvvm.fragment.AbstractFragment
import com.kidssavetheocean.fatechanger.presentation.mvvm.vm.EmptyViewModel
import dagger.hilt.android.AndroidEntryPoint

//todo this and UserIdentificationFragment might be only one fragment

@AndroidEntryPoint
class UserIdentificationCardFragment : AbstractFragment<FragmentUserIdentificationCardBinding, EmptyViewModel>() {

    override fun getViewModelResId(): Int = BR.emptyVM

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java

    override fun getLayoutResId(): Int = R.layout.fragment_user_identification_card

    fun setBackgroundImage(backgroundImageId: Int) {
        val multiTransformation = MultiTransformation(
            CenterCrop(),
            //todo fix this uses 'jp.wasabeef:glide-transformations:3.3.0'
//            RoundedCornersTransformation(50, 0)
        )

        Glide.with(this)
            .load(backgroundImageId)
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(binding.userIdentificationImageButton)
    }

    fun setUserTypeText(textId: Int) = binding.userIdentificationTypeTextView.setText(textId)

    fun setDesciption(textId: Int) = binding.description.setText(textId)

    fun setUpImageButtonOnClick(type: String) {
        binding.userIdentificationImageButton.setOnClickListener { v ->
            val intent = Intent(activity, IntroductionVideoActivity::class.java)
            intent.putExtra(IntroductionVideoActivity.INTRO_TYPE, type)
            startActivity(intent)
        }
    }
}