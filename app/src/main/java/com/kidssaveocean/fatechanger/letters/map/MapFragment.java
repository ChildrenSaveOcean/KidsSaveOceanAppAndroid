package com.kidssaveocean.fatechanger.letters.map;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.kidssaveocean.fatechanger.R;
import com.kidssaveocean.fatechanger.letters.LetterInjection;
import com.kidssaveocean.fatechanger.letters.data.Letter;
import com.kidssaveocean.fatechanger.letters.list.LettersListPresenter;

import java.util.List;


public class MapFragment extends Fragment implements OnMapReadyCallback, MapContract.View {

    MapContract.Presenter presenter;

    //region new instance
    public static MapFragment newInstance() {

        Bundle args = new Bundle();

        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MapFragment() {
        // Required empty public constructor
    }
    //endregion

    //region lifecycle


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        presenter = new MapPresenter(LetterInjection.provideLettersRepository(context), this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        FragmentManager fragmentManager = getChildFragmentManager();
        SupportMapFragment fragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.map);
        if (fragment != null)
            fragment.getMapAsync(this);
        return view;
    }
    //endregion

    //region implementation OnMapReadyCallback
    @Override
    public void onMapReady(GoogleMap googleMap) {
        presenter.loadLetter();
    }
    //endregion

    //region implementation MapContract.View

    @Override
    public void setLetters(List<Letter> cachedLetters) {
        // Todo: draw markers
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        /*
        LatLng sydney = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        */
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    //endregion
}
