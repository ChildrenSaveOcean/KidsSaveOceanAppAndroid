package com.kidssaveocean.fatechanger.bottomNavigation;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kidssaveocean.fatechanger.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.widget.Toast.makeText;

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

            switch (menuItem.getItemId()) {
                case R.id.action_world:
                    Toast.makeText(BottomNavigationActivity.this,"Home", Toast.LENGTH_LONG).show();
                    break;

                case R.id.action_news:
                     Toast.makeText(BottomNavigationActivity.this,"News", Toast.LENGTH_LONG).show();
                     break;

                case R.id.action_donate:
                     Toast.makeText(BottomNavigationActivity.this,"Donate", Toast.LENGTH_LONG).show();
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

                return true;
            };

        return listener;
    }

}
