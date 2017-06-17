package com.telran.borislav.masterhairsalonproject.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.telran.borislav.masterhairsalonproject.R;

/**
 * Created by Boris on 17.06.2017.
 */

public class ChoseRegistration extends Fragment implements View.OnClickListener {
    private ImageView masterImg, clientImg;
    private ChoseRegistrationListener listener;

    public void setListener(ChoseRegistrationListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.client_or_master, container, false);
        masterImg = (ImageView) view.findViewById(R.id.master_img);
        clientImg = (ImageView) view.findViewById(R.id.client_img);
        masterImg.setOnClickListener(this);
        clientImg.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.master_img:
                listener.toMasterRegister();
                break;
            case R.id.client_img:
                listener.toClientRegister();
                break;
        }
    }

    public interface ChoseRegistrationListener {
        void toMasterRegister();

        void toClientRegister();
    }
}
