package com.kidssaveocean.fatechanger.onboarding.userIdentification

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.kidssaveocean.fatechanger.R



class UserIdentificationFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_user_identification, container, false)

        var studentFragment = childFragmentManager.findFragmentById(R.id.student_fragment) as UserIdentificationCardFragment?
        var teacherFragment = childFragmentManager.findFragmentById(R.id.teacher_fragment) as UserIdentificationCardFragment?
        var othersFragment = childFragmentManager.findFragmentById(R.id.others_fragment) as UserIdentificationCardFragment?

        studentFragment!!.setBackgroundImage(R.drawable.experience_card_student)
        studentFragment!!.setUserTypeText(R.string.i_am_a_student)
        studentFragment!!.setDesciption(R.string.i_am_a_student_description)
        studentFragment!!.setUpImageButtonOnClick(getString(R.string.students))

        teacherFragment!!.setBackgroundImage(R.drawable.experience_card_teacher)
        teacherFragment!!.setUserTypeText(R.string.i_am_a_teacher)
        teacherFragment!!.setDesciption(R.string.i_am_a_teacher_description)
        teacherFragment!!.setUpImageButtonOnClick(getString(R.string.teachers))

        othersFragment!!.setBackgroundImage(R.drawable.experience_card_support)
        othersFragment!!.setUserTypeText(R.string.neither_student_nor_teacher)
        othersFragment!!.setDesciption(R.string.neither_student_nor_teacher_description)
        othersFragment!!.setUpImageButtonOnClick(getString(R.string.others))

        return view
    }
}
