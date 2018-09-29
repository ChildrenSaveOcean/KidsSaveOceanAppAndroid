package com.kidssaveocean.fatechanger.letters.master;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;

import com.kidssaveocean.fatechanger.letters.list.LettersListFragment;
import com.kidssaveocean.fatechanger.letters.map.MapFragment;

/**
 * Difference between FragmentPagerAdapter & FragmentStatePagerAdapter
 * Source: https://medium.com/inloopx/adventures-with-fragmentstatepageradapter-4f56a643f8e0
 */
public class LettersPagerAdapter extends FragmentStatePagerAdapter {

    /**
     * say if it's ready for instantiate the Fragments
     */
    private boolean isReady = false;

    LettersPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    //region implementation FragmentPagerAdapter

    @Override
    public Fragment getItem(int position) {
        if(!isReady) {
            return new Fragment();
        }
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

    @Override
    public int getItemPosition(@NonNull Object object){
        return PagerAdapter.POSITION_NONE;
    }
    //endregion

    /**
     * set isReady to true and refresh the view
     */
    void ready() {
        isReady = true;
        notifyDataSetChanged();
    }

}
