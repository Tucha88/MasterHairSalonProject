package com.telran.borislav.masterhairsalonproject.Fragments.MasterFragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.squareup.okhttp.Response;
import com.telran.borislav.masterhairsalonproject.Models.Provider;
import com.telran.borislav.masterhairsalonproject.Models.Services;
import com.telran.borislav.masterhairsalonproject.R;
import com.telran.borislav.masterhairsalonproject.Utilitis.Utils;

import java.io.IOException;

/**
 * Created by vadim on 02.04.2017.
 */

public class FragmentServicesAdd extends Fragment {
    private EditText inputServices, inputPrice, inputTime;
    private FloatingActionButton saveBtn;
    private AddItemTask addItemTask;
    private ProgressBar addItemProgress;

    private AddItemFragmentListener mListener;

    public FragmentServicesAdd() {
        // Required empty public constructor
    }

    public void setmListener(AddItemFragmentListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_services_add, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inputServices = (EditText) view.findViewById(R.id.input_custom_serv);
        inputPrice = (EditText) view.findViewById(R.id.input_custom_price);
        inputTime = (EditText) view.findViewById(R.id.input_custom_time);
        addItemProgress = (ProgressBar) view.findViewById(R.id.add_item_progress);
        saveBtn = (FloatingActionButton) view.findViewById(R.id.fab_save_item);
        final String srv = inputServices.getText().toString();
        final String prc = inputPrice.getText().toString();
        final String tm = inputTime.getText().toString();
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemTask = new AddItemTask(getContext());
                addItemTask.execute();
            }
        });
    }

/*    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddItemFragmentListener) {
            mListener = (AddItemFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AddItemFragmentListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        addItemTask = null;
    }

    public interface AddItemFragmentListener {
        void callback();
    }

    private class AddItemTask extends AsyncTask<Void,Void,String> {
        private final String path = "/master/service";
        private String name,info;
        private int price,duration;
        private Context context;

        public AddItemTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            name = String.valueOf(inputServices.getText());
            price = Integer.parseInt(String.valueOf(inputPrice.getText()));
            duration = Integer.parseInt(String.valueOf(inputTime.getText()));
            inputServices.setVisibility(View.INVISIBLE);
            inputPrice.setVisibility(View.INVISIBLE);
            inputTime.setVisibility(View.INVISIBLE);
            saveBtn.setVisibility(View.INVISIBLE);
            addItemProgress.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(Void... params) {
            String result = Utils.SERVER_RESPONSE_OK;
            Services serv = new Services();
            serv.setDuration(duration);
            serv.setName(name);
            serv.setPrice(price);
            Gson gson = new Gson();
            String json = gson.toJson(serv,Services.class);
            try {


                Response response = Provider.getInstance().put(path, json, getActivity().getSharedPreferences(Utils.AUTH,Context.MODE_PRIVATE).getString(Utils.TOKEN,""));
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
        protected void onPostExecute(final String aVoid) {
            super.onPostExecute(aVoid);
            inputServices.setVisibility(View.VISIBLE);
            inputPrice.setVisibility(View.VISIBLE);
            inputTime.setVisibility(View.VISIBLE);
            saveBtn.setVisibility(View.VISIBLE);
            addItemProgress.setVisibility(View.INVISIBLE);
            if (mListener!=null){
                mListener.callback();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            addItemTask = null;
            inputServices.setVisibility(View.VISIBLE);
            inputPrice.setVisibility(View.VISIBLE);
            inputTime.setVisibility(View.VISIBLE);
            saveBtn.setVisibility(View.VISIBLE);
            addItemProgress.setVisibility(View.INVISIBLE);
        }
    }
}
