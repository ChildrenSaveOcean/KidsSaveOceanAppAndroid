package com.kidssaveocean.fatechanger.letters.master;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kidssaveocean.fatechanger.letters.list.LettersListFragment;
import com.kidssaveocean.fatechanger.letters.map.MapFragment;

public class LettersPagerAdapter extends FragmentPagerAdapter {

    public LettersPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    //region implementation FragmentPagerAdapter

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MapFragment.newInstance();
            case 1:
                return LettersListFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Map"; // Todo: hardcoded text
            case 1:
                return "List";
            default:
                return null;
        }
    }

    //endregion
}
