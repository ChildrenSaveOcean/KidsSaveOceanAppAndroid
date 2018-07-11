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
        studentResourceCard.setListTitle("For Student");

        List<GeneralImageListCardView.GeneralImageListCard> cardList = new ArrayList<>();
        cardList.add(new GeneralImageListCardView.GeneralImageListCard(0,"Example 1", "sfsdfdsfdsfdsfds"));
        cardList.add(new GeneralImageListCardView.GeneralImageListCard(0,"Example 2", "sfsdfdsfdsfdsfds"));
        cardList.add(new GeneralImageListCardView.GeneralImageListCard(0,"Example 3", "sfsdfdsfdsfdsfds"));
        cardList.add(new GeneralImageListCardView.GeneralImageListCard(0,"Example 4", "sfsdfdsfdsfdsfds"));
        cardList.add(new GeneralImageListCardView.GeneralImageListCard(0,"Example 5", "sfsdfdsfdsfdsfds"));
        cardList.add(new GeneralImageListCardView.GeneralImageListCard(0,"Example 6", "sfsdfdsfdsfdsfds"));
        cardList.add(new GeneralImageListCardView.GeneralImageListCard(0,"Example 7", "sfsdfdsfdsfdsfds"));
        cardList.add(new GeneralImageListCardView.GeneralImageListCard(0,"Example 8", "sfsdfdsfdsfdsfds"));
        cardList.add(new GeneralImageListCardView.GeneralImageListCard(0,"Example 9", "sfsdfdsfdsfdsfds"));

        GeneralImageListCardView.GeneralImageListCardViewAdapter adapter = new GeneralImageListCardView.GeneralImageListCardViewAdapter(getContext(), cardList);
        studentResourceCard.setAdapter(adapter);

//        teacherResourceCard = (GeneralImageListCardView) view.findViewById(R.id.teachers_resource_card);
//        teacherResourceCard.setListTitle("For Teacher");
//        teacherResourceCard.setAdapter(adapter);


        return view;
    }



}
