package com.telran.borislav.masterhairsalonproject.Fragments.ClientFragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.telran.borislav.masterhairsalonproject.Models.Client;
import com.telran.borislav.masterhairsalonproject.Models.Token;
import com.telran.borislav.masterhairsalonproject.R;
import com.telran.borislav.masterhairsalonproject.Tasks.GetClientProfileTask;
import com.telran.borislav.masterhairsalonproject.Utilitis.Utils;

/**
 * Created by Boris on 20.04.2017.
 */

public class PersonalProfileFragment extends Fragment implements View.OnClickListener, GetClientProfileTask.AsyncResponse {
    private TextView clientFullName, clientEmail, clientPhoneNumber;
    private Button findMasterBtn;
    private ImageView favoriteMasters, editProfile;
    private Client client;

    private EditText clientFirstNameEditText, clientLastNameEditText, clientPhoneNumberEditText;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_client_personal_profile, container, false);
        clientEmail = (TextView) view.findViewById(R.id.client_name_txt);
        clientFullName = (TextView) view.findViewById(R.id.client_last_name_txt);
        clientPhoneNumber = (TextView) view.findViewById(R.id.client_phone_txt);
        findMasterBtn = (Button) view.findViewById(R.id.find_master_btn);

        findMasterBtn.setOnClickListener(this);
        setViewClientProfile();
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.find_master_btn:

                break;
        }
    }

    @Override
    public void processFinish() {
        setViewClientProfile();
    }

    @Override
    public void processStart() {

    }

    @Override
    public void processError(String s) {

    }

    private Token getToken() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("AUTH", Context.MODE_PRIVATE);
        Token token = new Token();

        token.setToken(sharedPreferences.getString("TOKEN", ""));
//        if (token.equals("")){
//            return null;
//        }
        return token;
    }

    private void setViewClientProfile() {
        try {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("PERSONAL", getActivity().MODE_PRIVATE);
            Gson gson = new Gson();
            client = gson.fromJson(sharedPreferences.getString("CLIENT", null), Client.class);
            if (client == null) {
                new GetClientProfileTask(getToken(), Utils.INFO_CLIENT, getActivity(), this).execute();
                return;
            }
            clientFullName.setText(client.getClientLastName() + " " + client.getClientName());
            clientPhoneNumber.setText(client.getClientPhoneNumber());
            clientEmail.setText(client.getClientEmail());
        } catch (Exception e) {
            clientFullName.setText("Enter your name");
            clientPhoneNumber.setText("enter your phone number");
            e.printStackTrace();

        }

    }

    private void changeLayout() {

    }


}
