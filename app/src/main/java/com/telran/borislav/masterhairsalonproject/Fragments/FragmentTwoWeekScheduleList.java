package com.telran.borislav.masterhairsalonproject.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.telran.borislav.masterhairsalonproject.Adapters.MyTwoWeekListAdapter;
import com.telran.borislav.masterhairsalonproject.Models.CalendarDayCustom;
import com.telran.borislav.masterhairsalonproject.R;
import com.telran.borislav.masterhairsalonproject.Tasks.GetMyTwoWeekSchedule;
import com.telran.borislav.masterhairsalonproject.Utilitis.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boris on 11.06.2017.
 */

public class FragmentTwoWeekScheduleList extends Fragment implements GetMyTwoWeekSchedule.ScheduleAsyncResponse, MyTwoWeekListAdapter.ScheduleClickListener {
    private FrameLayout progressFrame;
    private FloatingActionButton fabAddItem;
    private RecyclerView myList;
    private MyTwoWeekListAdapter adapter;
    private FragmentScheduleTemplate.UpdateListTask updateListTask;
    private FragmentScheduleTemplate.FragmentTemplateListListener listener;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isSwipedForRefresh = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedul_two_weeks, container, false);
        myList = (RecyclerView) view.findViewById(R.id.recycler_view_schedule_list);
        myList.setLayoutManager(new LinearLayoutManager(getActivity()));
        myList.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        myList.setItemAnimator(new DefaultItemAnimator());
        adapter = new MyTwoWeekListAdapter(getActivity());
        myList.setAdapter(adapter);
        adapter.setmClickListener(this);
        buildAdapter();
//        adapter.setOnItemClickListener(this);
        return view;
    }

    private void buildAdapter() {
        new GetMyTwoWeekSchedule(this, getActivity().getSharedPreferences(Utils.AUTH, Context.MODE_PRIVATE).getString(Utils.TOKEN, ""), "/master/all_timetable", getActivity()).execute();
    }


    @Override
    public void processFinishTwoWeeks() {
        Gson gson = new Gson();
        TypeToken<List<CalendarDayCustom>> typeToken = new TypeToken<List<CalendarDayCustom>>() {
        };
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Utils.PROFILE, Context.MODE_PRIVATE);
        String result = sharedPreferences.getString(Utils.CALENDAR_DAYS, "");
        if (result.equals("")) {
            Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_LONG).show();
            return;
        }
        ArrayList<CalendarDayCustom> calendarDays = gson.fromJson(result, typeToken.getType());
        for (int i = 0; i < calendarDays.size(); i++) {
            adapter.addItemAtFront(calendarDays.get(i));
        }
    }

    @Override
    public void profileGetErrorTwoWeeks(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onItemClick(int position) {

    }
}
