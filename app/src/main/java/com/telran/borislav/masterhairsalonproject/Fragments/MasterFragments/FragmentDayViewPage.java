package com.telran.borislav.masterhairsalonproject.Fragments.MasterFragments;

import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.telran.borislav.masterhairsalonproject.Models.CalendarDayCustom;
import com.telran.borislav.masterhairsalonproject.Models.Record;
import com.telran.borislav.masterhairsalonproject.R;
import com.telran.borislav.masterhairsalonproject.Utilitis.Utils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Boris on 12.06.2017.
 */

public class FragmentDayViewPage extends Fragment implements View.OnClickListener {
    List<String> listDataHeader;
    HashMap<String, Record> listDataChild;
    private TextView startHourDayView, endHourDayView;
    private ExpandableListView records;
    private CalendarDayCustom calendarDayCustom;
    private int mHour, mMinute;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_view, container, false);
        startHourDayView = (TextView) view.findViewById(R.id.start_hour_day_view);
        endHourDayView = (TextView) view.findViewById(R.id.end_hour_day_view);
        records = (ExpandableListView) view.findViewById(R.id.fragment_day_view_list);
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
                                String i = hourOfDay + ":  " + minute;

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

    private void prepareListData() {
//        listDataHeader = new ArrayList<>();
//        listDataChild = new HashMap<String, Record>();
//        for (int i = 0; i < calendarDayCustom.getRecords().size(); i++) {
//            listDataHeader.add(String.valueOf(calendarDayCustom.getRecords().get(i).getStarTime().getHourLight()) +
//                    ":" + String.valueOf(calendarDayCustom.getRecords().get(i).getStarTime().getMinuteLight()));
//            listDataChild.put(listDataHeader.get(i),calendarDayCustom.getRecords().get(i));
//        }
    }

    private class MyRecordsDayListAdapter extends BaseExpandableListAdapter {
        private Context _context;
        private List<String> _listDataHeader; // header titles
        // child data in format of header title, child title
        private HashMap<String, Record> _listDataChild;

        public MyRecordsDayListAdapter(Context _context, List<String> _listDataHeader, HashMap<String, Record> _listDataChild) {
            this._context = _context;
            this._listDataHeader = _listDataHeader;
            this._listDataChild = _listDataChild;
        }

        @Override
        public int getGroupCount() {
            return this._listDataHeader.size();
        }


        @Override
        public int getChildrenCount(int groupPosition) {
            return 1;
        }


        @Override
        public Object getGroup(int groupPosition) {
            return this._listDataHeader.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition));
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            String headerTitle = (String) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.service_row, null);
            }

            TextView lblListHeader = (TextView) convertView.findViewById(R.id.start_time_view_page);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle);

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            final Record childText = (Record) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.record_row, null);
            }

            TextView serviceNameTxt = (TextView) convertView.findViewById(R.id.service_name_text_row);
            TextView durationTxt = (TextView) convertView.findViewById(R.id.duration_view_page);
            TextView clientInfoTxt = (TextView) convertView.findViewById(R.id.client_info_view_page);
            TextView additionalInfoTt = (TextView) convertView.findViewById(R.id.additional_info_view_page);
            durationTxt.setText(childText.getDuration());
            clientInfoTxt.setText(childText.getClient());
            additionalInfoTt.setText(childText.getInfo());


            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
