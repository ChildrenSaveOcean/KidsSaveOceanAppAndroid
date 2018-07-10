package com.kidssaveocean.fatechanger.donation;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kidssaveocean.fatechanger.R;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class DonationFragment extends Fragment {

    public DonationFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donation, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.donate_button)
    public void pressDonateButton (Button button) {
        Uri uri = Uri.parse("https://www.paypal.com");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

}
