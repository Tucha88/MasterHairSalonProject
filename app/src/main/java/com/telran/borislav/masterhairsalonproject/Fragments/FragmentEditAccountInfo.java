package com.telran.borislav.masterhairsalonproject.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.telran.borislav.masterhairsalonproject.Models.AddressMaster;
import com.telran.borislav.masterhairsalonproject.Models.Master;
import com.telran.borislav.masterhairsalonproject.R;
import com.telran.borislav.masterhairsalonproject.Utilitis.Utils;

import java.io.IOException;

/**
 * Created by Boris on 09.06.2017.
 */

public class FragmentEditAccountInfo extends Fragment implements View.OnClickListener {
    public static final String TAG = "ONTAG";
    private static final String PATH = "/master/update";
    private EditText email, phoneNumber, name, lastName, addresses;
    private Spinner masterType;
    private int position;
    private Master master;
    private Button btnSave, btnBack;
    private Handler handler;
    private onClickListenerFromEditInfo listener;

    public void setListener(onClickListenerFromEditInfo listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_info, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        handler = new Handler();
        btnSave = (Button) view.findViewById(R.id.frag_btn_save);
        btnSave.setOnClickListener(this);
        email = (EditText) view.findViewById(R.id.input_email);
        phoneNumber = (EditText) view.findViewById(R.id.input_phone_number);
        name = (EditText) view.findViewById(R.id.input_name);
        lastName = (EditText) view.findViewById(R.id.input_last_name);
        masterType = (Spinner) view.findViewById(R.id.input_master_type);
        addresses = (EditText) view.findViewById(R.id.input_address);
        btnBack = (Button) view.findViewById(R.id.frag_edit_my_profile_btn_back);
        btnBack.setOnClickListener(this);
        master = getMasterInfo();
        loadMasterInfo();


        super.onViewCreated(view, savedInstanceState);
    }

    private void loadMasterInfo() {
        email.setText(master.getEmail());
        phoneNumber.setText(master.getPhoneNumber());
        name.setText(master.getName());
        lastName.setText(master.getLastName());
        addresses.setText(master.getAddressMaster().getAddress());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.frag_btn_save) {
//            adapter.updateContact(master, position);
            getActivity().getSupportFragmentManager().popBackStack();
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Utils.AUTH, getActivity().MODE_PRIVATE);
            String token = sharedPreferences.getString(Utils.TOKEN, "");
            Log.d(TAG, "putMasterUpdate: " + token);
            MediaType type = MediaType.parse("application/json; charset=utf-8");

            master.setEmail(email.getText().toString());
            master.setPhoneNumber(phoneNumber.getText().toString());
            master.setName(name.getText().toString());
            master.setLastName(lastName.getText().toString());
            if (masterType.getSelectedItem().toString().equals("Select type")){
                return;
            }
            master.setMasterType(masterType.getSelectedItem().toString());
            AddressMaster addressMaster = master.getAddressMaster();
            addressMaster.setAddress(addresses.getText().toString());
            master.setAddresses(addresses.getText().toString());
            String json = new Gson().toJson(master);
            RequestBody body = RequestBody.create(type, json);
            Request request = new Request.Builder()
                    .url("https://hair-salon-personal.herokuapp.com/" + PATH)
                    .addHeader("Authorization", token)
                    .put(body)
                    .build();
            OkHttpClient clientOK = new OkHttpClient();
//            clientOK.setConnectTimeout(5, TimeUnit.SECONDS);
//            clientOK.setReadTimeout(5, TimeUnit.SECONDS);

            clientOK.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    e.printStackTrace();
                    handler.post(new ErrorRequest("Connection error"));
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    if (response.isSuccessful()) {
                        if (response.code() < 400) {

                            handler.post(new RequestOk());
                            listener.onClickListener();
                            return;
                        }
                    } else if (response.code() == 409) {
                        handler.post(new ErrorRequest(response.message() + " "));
                    } else handler.post(new ErrorRequest("Server error!"));
                }
            });
        } else if (v.getId() == R.id.frag_edit_my_profile_btn_back) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

    private Master getMasterInfo() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Utils.PROFILE, Context.MODE_PRIVATE);
        return new Gson().fromJson(sharedPreferences.getString(Utils.MASTER_PROFILE, ""), Master.class);
    }

    public interface onClickListenerFromEditInfo {
        void onClickListener();
    }

    class ErrorRequest implements Runnable {
        private String result;

        public ErrorRequest(String result) {
            this.result = result;
        }

        @Override
        public void run() {
            Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
        }
    }

    class RequestOk implements Runnable {

        @Override
        public void run() {

        }
    }



}
