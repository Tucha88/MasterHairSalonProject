package com.telran.borislav.masterhairsalonproject.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.telran.borislav.masterhairsalonproject.Models.CalendarDayCustom;

import java.util.ArrayList;

/**
 * Created by Boris on 13.06.2017.
 */

public class MyTwoWeekFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<CalendarDayCustom> items;


    public MyTwoWeekFragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
