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
import com.telran.borislav.masterhairsalonproject.Tasks.GetMyProfileTask;
import com.telran.borislav.masterhairsalonproject.Utilitis.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Boris on 09.06.2017.
 */

public class FragmentScheduleTemplate extends Fragment implements MyTemplateListAdapter.ClickListener, View.OnClickListener, GetMyProfileTask.AsyncResponse {

    //    private ArrayList<WeekDayCustom> weekDays;
    //   private FrameLayout progressFrame;
    private FloatingActionButton fabAddItem;
    private RecyclerView myList;
    private MyTemplateListAdapter adapter;
    private UpdateListTask updateListTask;
    private FragmentTemplateListListener listener;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isSwipedForRefresh = false;
    private int mHour, mMinute;


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
        buildAdapter();
        adapter.setOnItemClickListener(this);
        return view;
    }

    private void buildAdapter() {
        new GetMyProfileTask(this, getActivity().getSharedPreferences(Utils.AUTH, Context.MODE_PRIVATE).getString(Utils.TOKEN, ""), "/master/info", getActivity()).execute();
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
                        if (v.getId() == R.id.start_hour) {
                            WeekDay weekDay = adapter.getItem(position);
                            weekDay.setStartWork(i);
                            adapter.updateItem(weekDay, position);
                            adapter.notifyItemChanged(position);
                        } else {
                            WeekDay weekDay = adapter.getItem(position);
                            weekDay.setEndWork(i);
                            adapter.updateItem(weekDay, position);
                            adapter.notifyItemChanged(position);
                        }

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    @Override
    public void onClick(View v) {
        WeekDayCustom weekDayCustom = new WeekDayCustom();
        if (v.getId() == R.id.fab_save_template) {
            ArrayList<WeekDayCustom> weekDays = new ArrayList<>();
            for (int i = 0; i < adapter.getItemCount(); i++) {
                adapter.getItem(i);
                weekDays.add(weekDayCustom.nWeekDayCustom(adapter.getItem(i)));
            }
            new UpdateListTask(weekDays).execute();
        }
    }

    @Override
    public void processFinish() {
        adapter.clear();
        Master master = new Gson().fromJson(getActivity().getSharedPreferences(Utils.PROFILE, Context.MODE_PRIVATE).getString(Utils.MASTER_PROFILE, ""), Master.class);
        for (WeekDayCustom item : master.getAddressMaster().getWeekTemplate()) {
            WeekDay weekDay = new WeekDay();
            weekDay.setActiveDay(item.isActiveDay());
            weekDay.setStartWork(item.getStartWork().getHourLight() + ":" + item.getStartWork().getMinuteLight());
            weekDay.setEndWork(item.getEndWork().getHourLight() + ":" + item.getEndWork().getMinuteLight());
            adapter.addItemAtFront(weekDay);
        }
    }

    @Override
    public void profileGetError(String s) {

    }


    public interface FragmentTemplateListListener {
        void itemSelected(WeekDay item);
    }

    class UpdateListTask extends AsyncTask<Void, Void, String> {
        private ArrayList<WeekDayCustom> weekDays;

        public UpdateListTask(ArrayList<WeekDayCustom> weekDays) {
            this.weekDays = weekDays;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            adapter.clear();
//            if (!isSwipedForRefresh)
//                progressFrame.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(Void... params) {
            String result = Utils.SERVER_RESPONSE_OK;
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
            if (items.equals(Utils.SERVER_RESPONSE_OK)) {
                Toast.makeText(getActivity(), "OK", Toast.LENGTH_LONG).show();
                buildAdapter();
            }
//            swipeRefreshLayout.setRefreshing(false);
            isSwipedForRefresh = false;
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
