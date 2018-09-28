package com.kidssaveocean.fatechanger.letters;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kidssaveocean.fatechanger.R;


public class LettersMasterFragment extends Fragment implements LettersMasterContract.View {


    public LettersMasterFragment() {
        // Required empty public constructor
    }


    public static LettersMasterFragment newInstance() {
        LettersMasterFragment fragment = new LettersMasterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    //region lifecycle
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_letters_master, container, false);

        ViewPager viewPager = view.findViewById(R.id.pager);
        LettersPagerAdapter myPagerAdapter = new LettersPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        TabLayout tabLayout = view.findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);//.setUpWithViewPager(viewPager);

        return view;
    }
    //endregion

    //region implementation LettersMasterContract.View

    //endregion

}
