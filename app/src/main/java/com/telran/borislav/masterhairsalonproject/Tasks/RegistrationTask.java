package com.telran.borislav.masterhairsalonproject.Tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.squareup.okhttp.Response;
import com.telran.borislav.masterhairsalonproject.Models.Client;
import com.telran.borislav.masterhairsalonproject.Models.Provider;
import com.telran.borislav.masterhairsalonproject.Models.Token;
import com.telran.borislav.masterhairsalonproject.Utilitis.Utils;

import java.io.IOException;

/**
 * Created by Boris on 12.04.2017.
 */

public class RegistrationTask extends AsyncTask<Void, Void, String> {
    public AsyncResponseClientRegister delegate;
    private Client auth;
    private String path;
    private Context context;

    public RegistrationTask(Client auth, String path, Context context, AsyncResponseClientRegister delegate) {
        this.auth = auth;
        this.path = path;
        this.context = context;
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... voids) {
        String result = "Registration ok!";
        Gson gson = new Gson();
        String json = gson.toJson(auth);
        try {
            Response response = Provider.getInstance().post(path, json, "");
            if (response.code() < 400) {
                String responseBody = response.body().string();
                if (!responseBody.isEmpty()) {
                    Token token = gson.fromJson(responseBody, Token.class);
                    SharedPreferences sharedPreferences = context.getSharedPreferences(Utils.AUTH, Context.MODE_PRIVATE);
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
                result = response.body().string();
            } else {
                result = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
            result = "Connection error!";
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        if (s.equals("Registration ok!")) {
            delegate.processFinish();
        } else {
            delegate.processError(s);
        }

    }

    public interface AsyncResponseClientRegister {
        void processFinish();

        void processStart();

        void processError(String s);

    }
}
