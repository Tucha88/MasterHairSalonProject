package com.telran.borislav.masterhairsalonproject.Fragments;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Response;
import com.telran.borislav.masterhairsalonproject.Adapters.MyTemplateListAdapter;
import com.telran.borislav.masterhairsalonproject.Models.Master;
import com.telran.borislav.masterhairsalonproject.Models.Provider;
import com.telran.borislav.masterhairsalonproject.Models.WeekDay;
import com.telran.borislav.masterhairsalonproject.Models.WeekDayCustom;
import com.telran.borislav.masterhairsalonproject.R;
import com.telran.borislav.masterhairsalonproject.Utilitis.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Boris on 09.06.2017.
 */

public class FragmentScheduleTemplate extends Fragment implements MyTemplateListAdapter.ClickListener, View.OnClickListener {

    ArrayList<WeekDayCustom> weekDays = new ArrayList<>();
    //   private FrameLayout progressFrame;
    private FloatingActionButton fabAddItem;
    private RecyclerView myList;
    private MyTemplateListAdapter adapter;
    private UpdateListTask updateListTask;
    private FragmentTemplateListListener listener;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isSwipedForRefresh = false;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private WeekDayCustom weekDayCustom = new WeekDayCustom();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_template, container, false);
        fabAddItem = (FloatingActionButton) view.findViewById(R.id.fab_save_template);
        fabAddItem.setOnClickListener(this);
        myList = (RecyclerView) view.findViewById(R.id.recycler_view_template_list);
        myList.setLayoutManager(new LinearLayoutManager(getActivity()));
        myList.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        myList.setItemAnimator(new DefaultItemAnimator());
        adapter = new MyTemplateListAdapter(getActivity());
        myList.setAdapter(adapter);
        Master master = new Gson().fromJson(getActivity().getSharedPreferences(Utils.PROFILE, Context.MODE_PRIVATE).getString(Utils.MASTER_PROFILE, ""), Master.class);
        for (WeekDayCustom item : master.getAddressMaster().getWeekTemplate()) {
            WeekDay weekDay = new WeekDay();
            weekDay.setActiveDay(item.isActiveDay());
            weekDay.setStartWork(item.getStartWork().getHourLight() + ":" + item.getStartWork().getMinuteLight());
            weekDay.setEndWork(item.getEndWork().getHourLight() + ":" + item.getEndWork().getMinuteLight());
            adapter.addItemAtFront(weekDay);
        }
        adapter.setOnItemClickListener(this);
        return view;
    }


    @Override
    public void onItemClick(final int position, final View v) {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        String i = hourOfDay + ":" + minute;
                        TextView textView;
                        if (v.getId() == R.id.start_hour) {
                            textView = (TextView) v.findViewById(R.id.start_hour);
                            WeekDay weekDay = adapter.getItem(position);
                            weekDay.setStartWork(i);
                            adapter.removeItem(position);
                            adapter.addItem(weekDay, position);

                            adapter.notifyItemChanged(position);
                        } else {
                            textView = (TextView) v.findViewById(R.id.end_hour);
                            WeekDay weekDay = adapter.getItem(position);
                            weekDay.setEndWork(i);
                            adapter.removeItem(position);
                            adapter.addItem(weekDay, position);
                            adapter.notifyItemChanged(position);
                        }

//                        textView.setText(i);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    @Override
    public void onChecked(int position, boolean isChecked) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab_save_template) {
            for (int i = 0; i < adapter.getItemCount(); i++) {
                adapter.getItem(i);
                weekDays.add(weekDayCustom.nWeekDayCustom(adapter.getItem(i)));
            }
            new UpdateListTask().execute();
        }
    }


    public interface FragmentTemplateListListener {
        void itemSelected(WeekDay item);
    }

    class UpdateListTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            adapter.clear();
//            if (!isSwipedForRefresh)
//                progressFrame.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "Registration ok!";
            Gson gson = new Gson();
            ArrayList<WeekDayCustom> weekDayCustoms = weekDays;
            TypeToken<List<WeekDayCustom>> typeToken = new TypeToken<List<WeekDayCustom>>() {
            };

            String body = gson.toJson(weekDayCustoms, typeToken.getType());
            try {
                Response response = Provider.getInstance().put("/master/template", body, getActivity().getSharedPreferences(Utils.AUTH, Context.MODE_PRIVATE).getString(Utils.TOKEN, ""));
                if (response.code() < 400) {
                    String responseBody = response.body().string();
                    if (!responseBody.isEmpty()) {
                        result = response.message();
                    } else {
                        result = "Server did not answer!";
                    }
                } else if (response.code() == 409) {
                    result = response.message();
                } else {
                    result = response.message();
                }
            } catch (IOException e) {
                e.printStackTrace();
                result = "Connection error!";


            }

            return result;
        }

        @Override
        protected void onPostExecute(String items) {
            super.onPostExecute(items);
//            swipeRefreshLayout.setRefreshing(false);
            isSwipedForRefresh = false;
            Toast.makeText(getActivity(), items, Toast.LENGTH_LONG).show();
//            progressFrame.setVisibility(View.GONE);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            updateListTask = null;
            isSwipedForRefresh = false;
//            progressFrame.setVisibility(View.GONE);
        }
    }
}
