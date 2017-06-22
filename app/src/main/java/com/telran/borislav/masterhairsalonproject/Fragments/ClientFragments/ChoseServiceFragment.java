package com.telran.borislav.masterhairsalonproject.Fragments.ClientFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.telran.borislav.masterhairsalonproject.Adapters.MyChoseServiceAdapter;
import com.telran.borislav.masterhairsalonproject.Models.MasterCustom;
import com.telran.borislav.masterhairsalonproject.R;
import com.telran.borislav.masterhairsalonproject.Utilitis.Utils;

/**
 * Created by Boris on 21.06.2017.
 */

public class ChoseServiceFragment extends Fragment {
    private MasterCustom master;
    private ChoseServiceListener listener;
    private RecyclerView listView;
    private MyChoseServiceAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointment, container, false);
        listView = (RecyclerView) view.findViewById(R.id.chose_service_list);
        String s = getArguments().getString(Utils.CHOSE_SERVICE);
        Gson gson = new Gson();
        master = gson.fromJson(s, MasterCustom.class);
        adapter = new MyChoseServiceAdapter(getActivity());
        listView.setAdapter(adapter);

        if (master.getSerivce() != null || master.getSerivce().size() > 0) {
            for (int i = 0; i < master.getSerivce().size(); i++) {
                adapter.addItem(master.getSerivce().get(i));
            }
        }

        return view;
    }

    interface ChoseServiceListener {
        void onButtonClick();
    }
}
