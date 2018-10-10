package com.kidssaveocean.fatechanger.bottomNavigation;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.kidssaveocean.fatechanger.R;
import com.kidssaveocean.fatechanger.donation.DonationFragment;
import com.kidssaveocean.fatechanger.home.HomeFragment;
import com.kidssaveocean.fatechanger.map.MapFragment;
import com.kidssaveocean.fatechanger.news.NewsFragment;
import com.kidssaveocean.fatechanger.resources.ResourcesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BottomNavigationActivity extends AppCompatActivity {

    @BindView(R.id.bottom_navigation_bar) BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        ButterKnife.bind(this);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(setUpOnNavigationItemSelectedListener());

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, new HomeFragment());
        transaction.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener setUpOnNavigationItemSelectedListener() {
        BottomNavigationView.OnNavigationItemSelectedListener listener = menuItem -> {

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Fragment fragment;

            switch (menuItem.getItemId()) {
                case R.id.action_home:
                    fragment = new HomeFragment();
                    transaction.replace(R.id.fragment_container, fragment);
                    break;

                case R.id.action_news:
                    fragment = new NewsFragment();
                    transaction.replace(R.id.fragment_container, fragment);
                     break;

                case R.id.action_donate:
                     fragment = new DonationFragment();
                     transaction.replace(R.id.fragment_container, fragment);
                     break;

                 case R.id.action_resources:
                     fragment = new ResourcesFragment();
                     transaction.replace(R.id.fragment_container, fragment);
                     break;

                 case R.id.action_map:
                     fragment = new MapFragment();
                     transaction.replace(R.id.fragment_container, fragment);
                     break;

                 default:
                     return false;

            }

            transaction.commit();

            return true;
        };

        return listener;
    }

}
