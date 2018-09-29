package com.kidssaveocean.fatechanger.letters.master;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kidssaveocean.fatechanger.R;
import com.kidssaveocean.fatechanger.letters.Letter;

import java.util.List;


public class LettersMasterFragment extends Fragment implements LettersMasterContract.View {

    private LettersMasterContract.Presenter mPresenter;

    //region UI elements
    private TextView lettersTextView;
    private TextView countriesTextView;
    //endregion

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



        lettersTextView = view.findViewById(R.id.n_letters);
        countriesTextView = view.findViewById(R.id.n_countries);

        ViewPager viewPager = view.findViewById(R.id.pager);
        LettersPagerAdapter myPagerAdapter = new LettersPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        TabLayout tabLayout = view.findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);//.setUpWithViewPager(viewPager);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }
    //endregion

    //region implementation LettersMasterContract.View

    @Override
    public void setPresenter(LettersMasterPresenter lettersMasterPresenter) {
        mPresenter = lettersMasterPresenter;
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showLetters(List<Letter> letters) {

    }

    @Override
    public void noLetters() {

    }

    @Override
    public void showLoadingLettersError() {

    }

    @Override
    public void updateLettersText(String s) {
        lettersTextView.setText(s);
    }

    @Override
    public void updateCountriesText(String s) {
        countriesTextView.setText(s);
    }

    //endregion

}
