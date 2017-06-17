package com.telran.borislav.masterhairsalonproject.Fragments.MasterFragments;

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
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Response;
import com.telran.borislav.masterhairsalonproject.Adapters.MyServicesListAdapter;
import com.telran.borislav.masterhairsalonproject.Models.Provider;
import com.telran.borislav.masterhairsalonproject.Models.Services;
import com.telran.borislav.masterhairsalonproject.R;
import com.telran.borislav.masterhairsalonproject.Utilitis.Utils;
import com.telran.borislav.masterhairsalonproject.listeners.ItemListTouchListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boris on 09.06.2017.
 */

public class FragmentServicesList extends Fragment {
    private FrameLayout progressFrame;
    private FloatingActionButton fabAddItem;
    private TextView emptyListTxt;
    private RecyclerView myList;
    private MyServicesListAdapter adapter;
    private UpdateListTask updateListTask;
    private ListFragmentListener listener;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isSwipedForRefresh = false;


    public FragmentServicesList() {
        // Required empty public constructor
        adapter = new MyServicesListAdapter(getActivity());
    }


    public void setFragmentListener(ListFragmentListener listener){
        this.listener = listener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_services_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        fabAddItem = (FloatingActionButton) view.findViewById(R.id.fab_add_item);
        progressFrame = (FrameLayout) view.findViewById(R.id.my_list_progress_bar);
        progressFrame.setOnClickListener(null);
        emptyListTxt = (TextView) view.findViewById(R.id.empty_list_txt);
        myList = (RecyclerView) view.findViewById(R.id.rv_my_serv_list);
        swipeRefreshLayout = (SwipeRefreshLayout) view .findViewById(R.id.swipe_refresh_item_list);
        myList.setLayoutManager(new LinearLayoutManager(getActivity()));
        myList.setItemAnimator(new DefaultItemAnimator());
        myList.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        myList.setAdapter(adapter);
        updateListTask = new UpdateListTask();
        updateListTask.execute();

        myList.addOnItemTouchListener(new ItemListTouchListener(getActivity(), myList, new ItemListTouchListener.ClickListener() {
            @Override
            public void onClickItem(View view, int position) {
                if (listener!=null){
                    listener.itemSelected(adapter.getItem(position));
                }
            }
        }));

        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.addItem();
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isSwipedForRefresh = true;
                updateListTask = new UpdateListTask();
                updateListTask.execute();

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (updateListTask!=null){
            updateListTask.cancel(true);
            updateListTask = null;
        }
    }

    public interface ListFragmentListener{
        void itemSelected(Services item);
        void addItem();
    }

    class UpdateListTask extends AsyncTask<Void,Void,ArrayList<Services>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            adapter.clear();
            if (!isSwipedForRefresh)
                progressFrame.setVisibility(View.VISIBLE);

        }

        @Override
        protected ArrayList<Services> doInBackground(Void... params) {
            String result = "Registration ok!";
            Gson gson = new Gson();
            ArrayList<Services> services = new ArrayList<>();
            TypeToken<List<Services>> typeToken = new TypeToken<List<Services>>(){};
            try {
                Response response = Provider.getInstance().get("/master/services",getActivity().getSharedPreferences(Utils.AUTH,Context.MODE_PRIVATE).getString(Utils.TOKEN,""));
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
        protected void onPostExecute(ArrayList<Services> items) {
            super.onPostExecute(items);
            swipeRefreshLayout.setRefreshing(false);
            isSwipedForRefresh = false;
            if (items.size()>0) {
                for (Services item : items) {
                    adapter.addItemAtFront(item);
                }
                emptyListTxt.setVisibility(View.GONE);
            }else{
                emptyListTxt.setVisibility(View.VISIBLE);
            }
            progressFrame.setVisibility(View.GONE);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            updateListTask = null;
            isSwipedForRefresh = false;
            progressFrame.setVisibility(View.GONE);
        }
    }
}
