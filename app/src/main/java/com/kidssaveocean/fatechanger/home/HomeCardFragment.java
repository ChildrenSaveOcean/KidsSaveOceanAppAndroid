package com.kidssaveocean.fatechanger.home;


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
public class HomeCardFragment extends Fragment {

    @BindView(R.id.home_card_image_button) ImageButton backgroundImage;
    @BindView(R.id.type_text_view) TextView typeTextView;
    @BindView(R.id.title_text_view) TextView titleTextView;
    @BindView(R.id.subtitle_text_view) TextView subtitleTextView;

    public HomeCardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_card, container, false);
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

    public void setTypeText (int textId) {
        typeTextView.setText(textId);
    }

    public void setTitleText (int textId) {
        titleTextView.setText(textId);
    }

    public void setSubtitleTextView (int textId) {
        subtitleTextView.setText(textId);
    }

}
