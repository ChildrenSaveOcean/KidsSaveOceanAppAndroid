package com.kidssaveocean.fatechanger.resources


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.utility.GeneralImageListCardView

import java.util.ArrayList


class ResourcesFragment : Fragment() {

    private lateinit var studentResourceCard: GeneralImageListCardView
    private lateinit var teacherResourceCard: GeneralImageListCardView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_resources, container, false)
        studentResourceCard = view.findViewById<View>(R.id.students_resource_card) as GeneralImageListCardView
        studentResourceCard.listTitle = getString(R.string.for_student)

        val studentList = ArrayList<GeneralImageListCardView.GeneralImageListCard>()
        studentList.add(GeneralImageListCardView.GeneralImageListCard(R.drawable.letter, getString(R.string.sample_letters), getString(R.string.sample_letters_description)))
        studentList.add(GeneralImageListCardView.GeneralImageListCard(R.drawable.contacts, getString(R.string.country_contacts), getString(R.string.country_contacts_description)))
        studentList.add(GeneralImageListCardView.GeneralImageListCard(0, "Resources Tile", "Description goes here."))
        studentList.add(GeneralImageListCardView.GeneralImageListCard(0, "Resources Tile", "Description goes here."))
        studentList.add(GeneralImageListCardView.GeneralImageListCard(0, "Resources Tile", "Description goes here."))

        val studentAdapter = GeneralImageListCardView.GeneralImageListCardViewAdapter(context, studentList)
        studentResourceCard.setAdapter(studentAdapter)

        val teacherList = ArrayList<GeneralImageListCardView.GeneralImageListCard>()
        teacherList.add(GeneralImageListCardView.GeneralImageListCard(R.drawable.teaching, getString(R.string.teaching_9_to_12), ""))
        teacherList.add(GeneralImageListCardView.GeneralImageListCard(R.drawable.teaching, getString(R.string.teaching_13_to_15), ""))
        teacherList.add(GeneralImageListCardView.GeneralImageListCard(R.drawable.teaching, getString(R.string.teaching_16_to_18), ""))
        teacherList.add(GeneralImageListCardView.GeneralImageListCard(0, "Resources Tile", "Description goes here."))
        teacherList.add(GeneralImageListCardView.GeneralImageListCard(0, "Resources Tile", "Description goes here."))

        teacherResourceCard = view.findViewById<View>(R.id.teachers_resource_card) as GeneralImageListCardView
        val teacherAdapter = GeneralImageListCardView.GeneralImageListCardViewAdapter(context, teacherList)
        teacherResourceCard.listTitle = getString(R.string.for_teacher)
        teacherResourceCard.setAdapter(teacherAdapter)


        return view
    }

}
