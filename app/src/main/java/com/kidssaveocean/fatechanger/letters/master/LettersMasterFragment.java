package com.kidssaveocean.fatechanger.letters.master;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kidssaveocean.fatechanger.R;
import com.kidssaveocean.fatechanger.letters.data.Letter;

import java.util.List;


public class LettersMasterFragment extends Fragment implements LettersMasterContract.View {

    private LettersMasterContract.Presenter mPresenter;

    //region UI elements
    private TextView lettersTextView;
    private TextView countriesTextView;
    private ProgressBar progressBar;
    //endregion

    //region new instance
    public LettersMasterFragment() {
        // Required empty public constructor
    }


    public static LettersMasterFragment newInstance() {
        LettersMasterFragment fragment = new LettersMasterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    //endregion

    //region lifecycle

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
        tabLayout.setupWithViewPager(viewPager);

        progressBar = view.findViewById(R.id.progressBar);
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
        progressBar.setVisibility(active? View.VISIBLE : View.GONE);
    }

    @Override
    public void showLetters(List<Letter> letters) {

    }

    @Override
    public void noLetters() {
        Toast.makeText(getContext(), "noLetters", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingLettersError() {
        Toast.makeText(getContext(), "showLoadingLettersError", Toast.LENGTH_SHORT).show();
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
