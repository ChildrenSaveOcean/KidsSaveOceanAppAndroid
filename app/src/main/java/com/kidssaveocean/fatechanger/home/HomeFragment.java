package com.kidssaveocean.fatechanger.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kidssaveocean.fatechanger.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    HomeCardFragment firstFragment;
    HomeCardFragment secondFragment;
    HomeCardFragment thirdFragment;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        FragmentManager childFragmentManager = getChildFragmentManager();
        firstFragment = (HomeCardFragment) childFragmentManager.findFragmentById(R.id.first_fragment);
        secondFragment = (HomeCardFragment) childFragmentManager.findFragmentById(R.id.second_fragment);
        thirdFragment = (HomeCardFragment) childFragmentManager.findFragmentById(R.id.third_fragment);

        firstFragment.setBackgroundImage(R.drawable.letter_writing_map);
        firstFragment.setTypeText(R.string.updates);
        firstFragment.setTitleText(R.string.letter_writing_update);
        firstFragment.setSubtitleTextView(R.string.see_our_progress);

        secondFragment.setBackgroundImage(R.drawable.surfer);
        secondFragment.setTypeText(R.string.interview);
        secondFragment.setTitleText(R.string.peder_hill);
        secondFragment.setSubtitleTextView(R.string.q_and_a_with_founder);

        thirdFragment.setBackgroundImage(R.drawable.dolphins);
        thirdFragment.setTypeText(R.string.updates);
        thirdFragment.setTitleText(R.string.high_scores);
        thirdFragment.setSubtitleTextView(R.string.see_where_your_country_ranks);

        return view;
    }

}
