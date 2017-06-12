package com.telran.borislav.masterhairsalonproject.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.telran.borislav.masterhairsalonproject.Models.CalendarDayCustom;
import com.telran.borislav.masterhairsalonproject.R;
import com.telran.borislav.masterhairsalonproject.Utilitis.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boris on 12.06.2017.
 */

public class FragmentTwoWeekSchedulePageView extends Fragment {
    private CalendarDayCustom calendarDayCustom;
    private ArrayList<CalendarDayCustom> calendarDays;
    private int position;
    private ViewPager pager;
    private FrameLayout frameLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_view_pager, container, false);
        pager = (ViewPager) view.findViewById(R.id.pager);
        frameLayout = (FrameLayout) view.findViewById(R.id.pager_one);
        Gson gson = new Gson();
        if (getArguments() != null || !getArguments().isEmpty()) {
            TypeToken<List<CalendarDayCustom>> typeToken = new TypeToken<List<CalendarDayCustom>>() {
            };
            calendarDayCustom = gson.fromJson(getArguments().getString(Utils.CALENDAR_DAY_BUNDLE), CalendarDayCustom.class);
            calendarDays = gson.fromJson(getArguments().getString(Utils.CALENDAR_DAYS_BUNDLE), typeToken.getType());
            position = getArguments().getInt(Utils.POSITION);
            FragmentDayViewPage fragment = new FragmentDayViewPage();
            getChildFragmentManager().beginTransaction().replace(R.id.pager, fragment, "FRAGMENT").commit();
        }


        return view;
    }
}
