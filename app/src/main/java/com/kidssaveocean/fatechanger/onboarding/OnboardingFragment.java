package com.kidssaveocean.fatechanger.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kidssaveocean.fatechanger.R;
import com.kidssaveocean.fatechanger.bottomNavigation.BottomNavigationActivity;
import com.kidssaveocean.fatechanger.userIdentification.UserIdentificationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class OnboardingFragment extends Fragment {

    public static final String IMAGE = "image";
    public static final String TITLE = "title";
    public static final String SUBTITLE = "subtitle";
    public static final String DESCRIPTION = "description";
    public static final String LAYOUT = "layout";

    @BindView(R.id.onboarding_image) ImageView onboardingImage;
    @BindView(R.id.title) TextView titleTextView;
    @BindView(R.id.subtitle) TextView subtitleTextView;
    @BindView(R.id.description) TextView descriptionTextView;

    public OnboardingFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        int imageId = bundle.getInt(IMAGE);
        int titleTextId = bundle.getInt(TITLE);
        int subTitleTextId = bundle.getInt(SUBTITLE);
        int descriptionTextId = bundle.getInt(DESCRIPTION);
        int layoutId = bundle.getInt(LAYOUT);

        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(layoutId, container, false);
        ButterKnife.bind(this, layout);

        onboardingImage.setImageDrawable(getResources().getDrawable(imageId));
        titleTextView.setText(titleTextId);
        subtitleTextView.setText(subTitleTextId);
        descriptionTextView.setText(descriptionTextId);

        return layout;
    }

    @Optional
    @OnClick(R.id.start_button)
    public void clickStart (Button startButton) {
        Intent intent = new Intent(getActivity(), BottomNavigationActivity.class);
        startActivity(intent);
        return;
    }


}
