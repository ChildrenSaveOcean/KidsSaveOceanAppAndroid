package com.kidssaveocean.fatechanger.resources;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kidssaveocean.fatechanger.R;
import com.kidssaveocean.fatechanger.utility.GeneralImageListCardView;

import java.util.ArrayList;
import java.util.List;

public class ResourcesFragment extends Fragment {

    GeneralImageListCardView studentResourceCard;
    GeneralImageListCardView teacherResourceCard;


    public ResourcesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resources, container, false);
        studentResourceCard = (GeneralImageListCardView) view.findViewById(R.id.students_resource_card);
        studentResourceCard.setListTitle(getString(R.string.for_student));

        List<GeneralImageListCardView.GeneralImageListCard> studentList = new ArrayList<>();
        studentList.add(new GeneralImageListCardView.GeneralImageListCard(R.drawable.letter, getString(R.string.sample_letters), getString(R.string.sample_letters_description)));
        studentList.add(new GeneralImageListCardView.GeneralImageListCard(R.drawable.contacts,getString(R.string.country_contacts), getString(R.string.country_contacts_description)));
        studentList.add(new GeneralImageListCardView.GeneralImageListCard(0,"Resources Tile", "Description goes here."));
        studentList.add(new GeneralImageListCardView.GeneralImageListCard(0,"Resources Tile", "Description goes here."));
        studentList.add(new GeneralImageListCardView.GeneralImageListCard(0,"Resources Tile", "Description goes here."));

        GeneralImageListCardView.GeneralImageListCardViewAdapter studentAdapter = new GeneralImageListCardView.GeneralImageListCardViewAdapter(getContext(), studentList);
        studentResourceCard.setAdapter(studentAdapter);

        List<GeneralImageListCardView.GeneralImageListCard> teacherList = new ArrayList<>();
        teacherList.add(new GeneralImageListCardView.GeneralImageListCard(R.drawable.teaching, getString(R.string.teaching_9_to_12), ""));
        teacherList.add(new GeneralImageListCardView.GeneralImageListCard(R.drawable.teaching,getString(R.string.teaching_13_to_15), ""));
        teacherList.add(new GeneralImageListCardView.GeneralImageListCard(R.drawable.teaching,getString(R.string.teaching_16_to_18), ""));
        teacherList.add(new GeneralImageListCardView.GeneralImageListCard(0,"Resources Tile", "Description goes here."));
        teacherList.add(new GeneralImageListCardView.GeneralImageListCard(0,"Resources Tile", "Description goes here."));

        teacherResourceCard = (GeneralImageListCardView) view.findViewById(R.id.teachers_resource_card);
        GeneralImageListCardView.GeneralImageListCardViewAdapter teacherAdapter = new GeneralImageListCardView.GeneralImageListCardViewAdapter(getContext(), teacherList);
        teacherResourceCard.setListTitle(getString(R.string.for_teacher));
        teacherResourceCard.setAdapter(teacherAdapter);


        return view;
    }



}
