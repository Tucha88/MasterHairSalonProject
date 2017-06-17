package com.telran.borislav.masterhairsalonproject.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.Response;
import com.telran.borislav.masterhairsalonproject.Models.AuthReg;
import com.telran.borislav.masterhairsalonproject.Models.Provider;
import com.telran.borislav.masterhairsalonproject.Models.Token;
import com.telran.borislav.masterhairsalonproject.R;
import com.telran.borislav.masterhairsalonproject.Utilitis.Utils;

import java.io.IOException;

/**
 * Created by Boris on 08.06.2017.
 */

public class LoginFragment extends Fragment implements View.OnClickListener {
    private EditText loginEditText, passwordEditText;
    private TextView createAccountTextView;
    private Button loginBtn;
    private TransactionControllerListener listener;
    private FrameLayout progressBar;
    private String login,password;

    public void setListener(TransactionControllerListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        loginEditText = (EditText) view.findViewById(R.id.email_login_text_edit);
        passwordEditText = (EditText) view.findViewById(R.id.password_text_edit);
        createAccountTextView = (TextView) view.findViewById(R.id.register_text_link);
        loginBtn = (Button) view.findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(this);
        createAccountTextView.setOnClickListener(this);
        setListener((TransactionControllerListener) getActivity());
        progressBar = (FrameLayout) view.findViewById(R.id.login_progress_bar);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                if (isLoginTextExist()){
                    new UserLoginTask(login,password).execute();
                }
                break;
            case R.id.register_text_link:
                listener.goToNextFragment();

                break;
        }
    }

    private boolean isLoginTextExist(){
        login = loginEditText.getText().toString();
        password = passwordEditText.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError(getString(R.string.error_invalid_password));
            return false;
        }
        if (TextUtils.isEmpty(login)) {
            loginEditText.setError(getString(R.string.error_field_required));
            return false;
        }
        return true;
    }


    public interface TransactionControllerListener {
        void goToNextFragment();

        void goToNextActivity();

        void showError(String s);
    }

    public class UserLoginTask extends AsyncTask<Void, Void, String> {
        private final String path = "/login/login";
        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected String doInBackground(Void... params) {
//           TODO: Request to server
            String result = "Login ok!";
            AuthReg auth = new AuthReg(mEmail, mPassword);
            Gson gson = new Gson();
            String json = gson.toJson(auth);
            try {
                Response response = Provider.getInstance().post(path, json, "");
                if (response.code() < 400) {
                    String responseBody = response.body().string();
                    if (!responseBody.isEmpty()) {
                        Token token = gson.fromJson(responseBody, Token.class);
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Utils.AUTH, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Utils.TOKEN, token.getToken());
                        if (token.isUser()) {
                            editor.putBoolean(Utils.MASTER_OR_CLIENT, true);
                        } else {
                            editor.putBoolean(Utils.MASTER_OR_CLIENT, false);
                        }
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
                if (success.equals("Login ok!")) {
                    listener.goToNextActivity();
                }else {
                    listener.showError(success);
                }
            }
        }


    }
}
