package com.telran.borislav.masterhairsalonproject.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.telran.borislav.masterhairsalonproject.R;

/**
 * Created by Boris on 12.06.2017.
 */

public class FragmentDayViewPage extends Fragment {
    private TextView startHourDayView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_view, container, false);
        startHourDayView = (TextView) view.findViewById(R.id.start_hour_day_view);
        startHourDayView.setText("Hello world");
        return view;
    }
}
