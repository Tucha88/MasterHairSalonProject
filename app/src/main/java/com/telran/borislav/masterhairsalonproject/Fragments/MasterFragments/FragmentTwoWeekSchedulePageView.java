package com.telran.borislav.masterhairsalonproject.Fragments.MasterFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_view_pager, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);

        Gson gson = new Gson();
        if (getArguments() != null || !getArguments().isEmpty()) {
            TypeToken<List<CalendarDayCustom>> typeToken = new TypeToken<List<CalendarDayCustom>>() {
            };
            calendarDayCustom = gson.fromJson(getArguments().getString(Utils.CALENDAR_DAY_BUNDLE), CalendarDayCustom.class);
            calendarDays = gson.fromJson(getArguments().getString(Utils.CALENDAR_DAYS_BUNDLE), typeToken.getType());
            position = getArguments().getInt(Utils.POSITION);

        }

//        getChildFragmentManager().beginTransaction().add(R.id.pager_one, fragment, "FRAGMENT").commit();

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        for (int i = 0; i < calendarDays.size(); i++) {
            adapter.addFrag(i, calendarDays.get(i));
        }
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position, false);
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(int position, CalendarDayCustom dayCustom) {
            FragmentDayViewPage fragment = new FragmentDayViewPage();
            Bundle bundle = new Bundle();
            Gson gson = new Gson();
            bundle.putString(Utils.CALENDAR_DAY_BUNDLE_FOR_DAY_VIEW, gson.toJson(dayCustom, CalendarDayCustom.class));
            fragment.setArguments(bundle);
            mFragmentList.add(fragment);
            mFragmentTitleList.add(dayCustom.getMyCalendar());
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
