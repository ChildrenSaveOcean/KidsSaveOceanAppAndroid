package com.kidssaveocean.fatechanger.onboarding.userIdentification

import android.view.View
import com.kidssaveocean.fatechanger.BR
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.databinding.FragmentUserIdentificationBinding
import com.kidssaveocean.fatechanger.presentation.mvvm.fragment.AbstractFragment
import com.kidssaveocean.fatechanger.presentation.mvvm.vm.EmptyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserIdentificationFragment : AbstractFragment<FragmentUserIdentificationBinding, EmptyViewModel>() {

    override fun onPrepareLayout(layoutView: View?) {
        super.onPrepareLayout(layoutView)
        //todo fix this
        val studentFragment = childFragmentManager.findFragmentById(R.id.student_fragment) as UserIdentificationCardFragment?
        val teacherFragment = childFragmentManager.findFragmentById(R.id.teacher_fragment) as UserIdentificationCardFragment?
        val othersFragment = childFragmentManager.findFragmentById(R.id.others_fragment) as UserIdentificationCardFragment?

        studentFragment!!.setBackgroundImage(R.drawable.experience_card_student)
        studentFragment.setUserTypeText(R.string.i_am_a_student)
        studentFragment.setDesciption(R.string.i_am_a_student_description)
        studentFragment.setUpImageButtonOnClick(getString(R.string.students))

        teacherFragment!!.setBackgroundImage(R.drawable.experience_card_teacher)
        teacherFragment.setUserTypeText(R.string.i_am_a_teacher)
        teacherFragment.setDesciption(R.string.i_am_a_teacher_description)
        teacherFragment.setUpImageButtonOnClick(getString(R.string.teachers))

        othersFragment!!.setBackgroundImage(R.drawable.experience_card_support)
        othersFragment.setUserTypeText(R.string.neither_student_nor_teacher)
        othersFragment.setDesciption(R.string.neither_student_nor_teacher_description)
        othersFragment.setUpImageButtonOnClick(getString(R.string.others))

    }

    override fun getViewModelResId(): Int = BR.emptyVM

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java

    override fun getLayoutResId(): Int = R.layout.fragment_user_identification
}
