package com.telran.borislav.masterhairsalonproject.Fragments;

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
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Response;
import com.telran.borislav.masterhairsalonproject.Adapters.MyTemplateListAdapter;
import com.telran.borislav.masterhairsalonproject.Models.Master;
import com.telran.borislav.masterhairsalonproject.Models.Provider;
import com.telran.borislav.masterhairsalonproject.Models.WeekDay;
import com.telran.borislav.masterhairsalonproject.R;
import com.telran.borislav.masterhairsalonproject.Utilitis.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boris on 09.06.2017.
 */

public class FragmentScheduleTemplate extends Fragment {

//   private FrameLayout progressFrame;
    private FloatingActionButton fabAddItem;
    private RecyclerView myList;
    private MyTemplateListAdapter adapter;
    private UpdateListTask updateListTask;
    private FragmentTemplateListListener listener;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isSwipedForRefresh = false;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_template, container, false);

        myList = (RecyclerView) view.findViewById(R.id.recycler_view_template_list);
        myList.setLayoutManager(new LinearLayoutManager(getActivity()));
        myList.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        myList.setItemAnimator(new DefaultItemAnimator());
        adapter = new MyTemplateListAdapter(getActivity());
        myList.setAdapter(adapter);

        Master master = new Gson().fromJson(getActivity().getSharedPreferences(Utils.PROFILE,Context.MODE_PRIVATE).getString(Utils.MASTER_PROFILE,""),Master.class);
        for (WeekDay item : master.getAddressMaster().getWeekTemplate()) {
            adapter.addItemAtFront(item);
        }
        return view;
    }



    public interface FragmentTemplateListListener{
        void itemSelected(WeekDay item);
    }

    class UpdateListTask extends AsyncTask<Void,Void,ArrayList<WeekDay>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            adapter.clear();
//            if (!isSwipedForRefresh)
//                progressFrame.setVisibility(View.VISIBLE);

        }

        @Override
        protected ArrayList<WeekDay> doInBackground(Void... params) {
            String result = "Registration ok!";
            Gson gson = new Gson();
            ArrayList<WeekDay> services = new ArrayList<>();
            TypeToken<List<WeekDay>> typeToken = new TypeToken<List<WeekDay>>(){};
            try {
                Response response = Provider.getInstance().get("/master/services",getActivity().getSharedPreferences(Utils.AUTH, Context.MODE_PRIVATE).getString(Utils.TOKEN,""));
                if (response.code() < 400) {
                    String responseBody = response.body().string();
                    if (!responseBody.isEmpty()) {
                        services = gson.fromJson(responseBody,typeToken.getType());

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

            return services;
        }

        @Override
        protected void onPostExecute(ArrayList<WeekDay> items) {
            super.onPostExecute(items);
            swipeRefreshLayout.setRefreshing(false);
            isSwipedForRefresh = false;
            if (items.size()>0) {
                for (WeekDay item : items) {
                    adapter.addItemAtFront(item);
                }

            }else{

            }
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
