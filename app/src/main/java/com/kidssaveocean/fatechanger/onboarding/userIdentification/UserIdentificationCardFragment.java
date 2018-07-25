package com.kidssaveocean.fatechanger.onboarding.userIdentification;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.kidssaveocean.fatechanger.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserIdentificationCardFragment extends Fragment {

    @BindView(R.id.user_identification_image_button) ImageButton backgroundImage;
    @BindView(R.id.user_identification_type_text_view) TextView userIdTypeTextView;
    @BindView(R.id.description) TextView descriptionTextView;

    public UserIdentificationCardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_identification_card, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void setBackgroundImage (int backgroundImageId) {
        MultiTransformation multiTransformation = new MultiTransformation(
                new CenterCrop(),
                new RoundedCornersTransformation(50, 0)
        );

        Glide.with(this)
                .load(backgroundImageId)
                .apply(RequestOptions.bitmapTransform(multiTransformation))
                .into(backgroundImage);
    }

    public void setUserTypeText (int textId) {
        userIdTypeTextView.setText(textId);
    }

    public void setDesciption (int textId) {
        descriptionTextView.setText(textId);
    }

    public void setUpImageButtonOnClick (String type) {
        backgroundImage.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), IntroductionVideoActivity.class);
            intent.putExtra(IntroductionVideoActivity.INTRO_TYPE, type);
            startActivity(intent);
        });
    }

}
