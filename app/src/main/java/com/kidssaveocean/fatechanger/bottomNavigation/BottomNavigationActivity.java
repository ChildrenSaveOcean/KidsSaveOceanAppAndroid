package com.kidssaveocean.fatechanger.bottomNavigation;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.kidssaveocean.fatechanger.R;
import com.kidssaveocean.fatechanger.donation.DonationFragment;

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
    }

    private BottomNavigationView.OnNavigationItemSelectedListener setUpOnNavigationItemSelectedListener() {
        BottomNavigationView.OnNavigationItemSelectedListener listener = menuItem -> {

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Fragment fragment;

            switch (menuItem.getItemId()) {
                case R.id.action_world:
                    Toast.makeText(BottomNavigationActivity.this,"Home", Toast.LENGTH_LONG).show();
                    break;

                case R.id.action_news:
                     Toast.makeText(BottomNavigationActivity.this,"News", Toast.LENGTH_LONG).show();
                     break;

                case R.id.action_donate:
                     fragment = new DonationFragment();
                     transaction.replace(R.id.fragment_container, fragment);
                     break;

                 case R.id.action_resources:
                     Toast.makeText(BottomNavigationActivity.this,"Resources", Toast.LENGTH_LONG).show();
                     break;

                 case R.id.action_map:
                     Toast.makeText(BottomNavigationActivity.this,"Map", Toast.LENGTH_LONG).show();
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
