package com.telran.borislav.masterhairsalonproject.Fragments.MasterFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Response;
import com.telran.borislav.masterhairsalonproject.Adapters.MySpinnerAdapter;
import com.telran.borislav.masterhairsalonproject.Models.Master;
import com.telran.borislav.masterhairsalonproject.Models.Provider;
import com.telran.borislav.masterhairsalonproject.Models.StateVO;
import com.telran.borislav.masterhairsalonproject.Models.Token;
import com.telran.borislav.masterhairsalonproject.R;
import com.telran.borislav.masterhairsalonproject.Utilitis.Utils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Boris on 08.06.2017.
 */

public class FragmentRegistration extends Fragment implements View.OnClickListener {

    private EditText inputName, inputLastName, inputEmail, inputPhone, inputPassword, inputReTypePassword;
    private Button nextBtnRegistration;
    private View progressViewReg;
    private View loginFormViewReg;
    private RadioButton men, women, all;
    private RadioGroup radioGroup;
    private FrameLayout containerLang;
    private Spinner spinner;

    private Master currentMaster = new Master();
    private Handler handler;
    private TextView resTXT;
    private MySpinnerAdapter mySpinnerAdapter;

    private StartMainActivityListener listener;

    public void setListener(StartMainActivityListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inputName = (EditText) view.findViewById(R.id.input_name);
        inputLastName = (EditText) view.findViewById(R.id.input_last_name);
        inputEmail = (EditText) view.findViewById(R.id.input_email);
        inputPhone = (EditText) view.findViewById(R.id.input_phone_number);
        inputPassword = (EditText) view.findViewById(R.id.input_password);
        inputReTypePassword = (EditText) view.findViewById(R.id.input_password2);
//        men = (RadioButton) view.findViewById(R.id.radio_men_btn);
//        women = (RadioButton) view.findViewById(R.id.radio_women_btn);
//        all = (RadioButton) view.findViewById(R.id.radio_all_btn);
        spinner = (Spinner) view.findViewById(R.id.language_spinner);
        radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
        resTXT = (TextView) view.findViewById(R.id.res_txt);
        nextBtnRegistration = (Button) view.findViewById(R.id.next_btn_registration);
        nextBtnRegistration.setOnClickListener(this);
        progressViewReg = view.findViewById(R.id.registration_progress);
        loginFormViewReg = view.findViewById(R.id.regestration_form);
        handler = new Handler();

        try {

            ArrayList<StateVO> listVOs = new ArrayList<>();
            StateVO stateVO = new StateVO();

            stateVO.setTitle("Chose language");
            listVOs.add(stateVO);
            listVOs.add(new StateVO("RUS", false));
            listVOs.add(new StateVO("HEB", false));
            listVOs.add(new StateVO("ENG", false));

            mySpinnerAdapter = new MySpinnerAdapter(getActivity(), 0, listVOs);
            spinner.setAdapter(mySpinnerAdapter);


        } catch (Exception e) {
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.next_btn_registration) {
            if (isInfoComplete()) {
                new RegistrationLoginTask().execute();
            }
        }
    }

    private boolean isInfoComplete() {
        currentMaster.setLang(new ArrayList<String>());
        for (int i = 1; i < spinner.getCount(); i++) {
            StateVO stateVO = mySpinnerAdapter.getItem(i);
            if (stateVO.isSelected()) {
                currentMaster.addLang(stateVO.getTitle());
            }
        }
        if (currentMaster.getLang().isEmpty()) {
            Toast.makeText(getActivity(), "Choose language", Toast.LENGTH_LONG).show();
            return false;
        }
        if (inputPassword.getText().toString().equals("")) {
            inputPassword.setError("Enter password");
        }
        if (!inputPassword.getText().toString().equals(inputReTypePassword.getText().toString())) {
            inputReTypePassword.setError("Enter password correctly");
            return false;
        }
        if (inputEmail.getText().toString().equals("")) {
            inputEmail.setError("Enter email");
            return false;
        }
        currentMaster.setEmail(inputEmail.getText().toString());
        currentMaster.setPassword(inputPassword.getText().toString());
        currentMaster.setLastName(inputLastName.getText().toString());
        currentMaster.setName(inputName.getText().toString());
        currentMaster.setPhoneNumber(inputPhone.getText().toString());
        if (radioGroup.getCheckedRadioButtonId() == R.id.radio_men_btn){
            currentMaster.setMasterType("Men");
        } else if (radioGroup.getCheckedRadioButtonId() == R.id.radio_women_btn){
            currentMaster.setMasterType("Woman");
        } else if (radioGroup.getCheckedRadioButtonId() == R.id.radio_all_btn){
            currentMaster.setMasterType("Universal");
        }
        return true;
    }


    public interface StartMainActivityListener {
        void startMainActivity();

        void myError(String s);
    }

    public class RegistrationLoginTask extends AsyncTask<Void, Void, String> {

        private final String path = "/register/master";

        public RegistrationLoginTask() {
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "Registration ok!";
            Gson gson = new Gson();
            String json = gson.toJson(currentMaster);
            try {
                Response response = Provider.getInstance().post(path, json, "");
                if (response.code() < 400) {
                    String responseBody = response.body().string();
                    if (!responseBody.isEmpty()) {
                        Token token = gson.fromJson(responseBody, Token.class);
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Utils.AUTH, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        if (token.isUser()) {
                            editor.putBoolean(Utils.MASTER_OR_CLIENT, true);
                        } else {
                            editor.putBoolean(Utils.MASTER_OR_CLIENT, false);
                        }
                        editor.putString(Utils.TOKEN, token.getToken());
                        editor.commit();
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
        protected void onPostExecute(final String success) {
            if (success != null) {
                if (success.equals("Registration ok!")) {
                    listener.startMainActivity();
                } else {
                    listener.myError(success);
                }
            } else {
                listener.myError(success);
            }
        }

    }

}


