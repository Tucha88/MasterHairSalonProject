package com.telran.borislav.masterhairsalonproject.Fragments;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.telran.borislav.masterhairsalonproject.Models.CalendarDayCustom;
import com.telran.borislav.masterhairsalonproject.R;
import com.telran.borislav.masterhairsalonproject.Utilitis.Utils;

import java.util.Calendar;

/**
 * Created by Boris on 12.06.2017.
 */

public class FragmentDayViewPage extends Fragment implements View.OnClickListener {
    private TextView startHourDayView, endHourDayView;
    private ListView records;
    private CalendarDayCustom calendarDayCustom;
    private int mHour, mMinute;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_view, container, false);
        startHourDayView = (TextView) view.findViewById(R.id.start_hour_day_view);
        endHourDayView = (TextView) view.findViewById(R.id.end_hour_day_view);
        records = (ListView) view.findViewById(R.id.fragment_day_view_list);
        Gson gson = new Gson();
        if (getArguments() != null || !getArguments().isEmpty()) {
            calendarDayCustom = gson.fromJson(getArguments().getString(Utils.CALENDAR_DAY_BUNDLE_FOR_DAY_VIEW), CalendarDayCustom.class);
            startHourDayView.setText(calendarDayCustom.getStartWork().getHourLight() + ":" + calendarDayCustom.getStartWork().getMinuteLight());
            endHourDayView.setText(calendarDayCustom.getEndWork().getHourLight() + ":" + calendarDayCustom.getEndWork().getMinuteLight());
            startHourDayView.setOnClickListener(this);
            endHourDayView.setOnClickListener(this);
        }
        return view;
    }

    @Override
    public void onClick(final View v) {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        switch (v.getId()) {
            case R.id.start_hour_day_view:
                mHour = calendarDayCustom.getStartWork().getHourLight();
                mMinute = calendarDayCustom.getStartWork().getMinuteLight();
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), 1,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                String i = hourOfDay + ":" + minute;

                                calendarDayCustom.getStartWork().setHourLight(hourOfDay);
                                calendarDayCustom.getStartWork().setMinuteLight(minute);
                                startHourDayView.setText(calendarDayCustom.getStartWork().getHourLight() + ":" + calendarDayCustom.getStartWork().getMinuteLight());

                            }
                        }, mHour, mMinute, true);
                timePickerDialog.show();

                return;
            case R.id.end_hour_day_view:
                mHour = calendarDayCustom.getEndWork().getHourLight();
                mMinute = calendarDayCustom.getEndWork().getMinuteLight();
                TimePickerDialog timePickerDialog1 = new TimePickerDialog(getActivity(), 1,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                String i = hourOfDay + ":" + minute;

                                calendarDayCustom.getEndWork().setHourLight(hourOfDay);
                                calendarDayCustom.getEndWork().setMinuteLight(minute);
                                endHourDayView.setText(calendarDayCustom.getEndWork().getHourLight() + ":" + calendarDayCustom.getEndWork().getMinuteLight());

                            }
                        }, mHour, mMinute, true);
                timePickerDialog1.show();

                return;
        }

    }

    private class MyRecordsDayListAdapter extends RecyclerView.Adapter<MyRecordsDayListAdapter.MyViewHolder> {


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return calendarDayCustom.getRecords().size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView servicesTxt, priceTxt, timeTxt;

            public MyViewHolder(View itemView) {
                super(itemView);
//                servicesTxt = ;
//                priceTxt = ;
//                timeTxt = ;
            }
        }
    }
}
