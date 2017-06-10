package com.telran.borislav.masterhairsalonproject.Tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.Response;
import com.telran.borislav.masterhairsalonproject.Models.MasterCustom;
import com.telran.borislav.masterhairsalonproject.Models.Provider;
import com.telran.borislav.masterhairsalonproject.Utilitis.Utils;

import java.io.IOException;

/**
 * Created by Boris on 09.06.2017.
 */

public class GetMyProfileTask extends AsyncTask<Void, Void, String> {
    public AsyncResponse delegate;
    private String token;
    private String path;
    private Context context;

    public GetMyProfileTask(AsyncResponse delegate, String token, String path, Context context) {
        this.delegate = delegate;
        this.token = token;
        this.path = path;
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String result = Utils.SERVER_RESPONSE_OK;
        Gson gson = new Gson();
        try {
            Response response = Provider.getInstance().get(path, token);
            Log.d("TAG", "doInBackground: " + response.message());
            if (response.code() < 400) {
                String responseBody = response.body().string();
                if (!responseBody.isEmpty()) {
                    MasterCustom client = gson.fromJson(responseBody, MasterCustom.class);
                    SharedPreferences sharedPreferences = context.getSharedPreferences(Utils.PROFILE, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Utils.MASTER_PROFILE, gson.toJson(client, MasterCustom.class));
                    editor.commit();

                } else {
                    result = response.message();
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
    protected void onPostExecute(String string) {
        if (string.equals(Utils.SERVER_RESPONSE_OK)){
            delegate.processFinish();
        }else {
            delegate.profileGetError(string);
        }


    }

    public interface AsyncResponse {
        void processFinish();
        void profileGetError(String s);
    }
}
