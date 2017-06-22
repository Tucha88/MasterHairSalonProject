package com.telran.borislav.masterhairsalonproject.Tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.squareup.okhttp.Response;
import com.telran.borislav.masterhairsalonproject.Models.MasterArray;
import com.telran.borislav.masterhairsalonproject.Models.Provider;
import com.telran.borislav.masterhairsalonproject.Utilitis.Utils;

import java.io.IOException;

/**
 * Created by Boris on 21.06.2017.
 */

public class GetAllMastersTask extends AsyncTask<Void, Void, MasterArray> {
    private GetAllMastersAsyncResponse delegate;
    private String token;
    private String path;
    private Context context;

    public GetAllMastersTask(GetAllMastersAsyncResponse delegate, String token, String path, Context context) {
        this.delegate = delegate;
        this.token = token;
        this.path = path;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected MasterArray doInBackground(Void... params) {
        String result;
        Gson gson = new Gson();
        MasterArray masterArray = new MasterArray();
        try {
            Response response = Provider.getInstance().get(path, token);
            if (response.code() < 400) {
                String responseBody = response.body().string();
                if (!responseBody.isEmpty()) {
                    masterArray = gson.fromJson(responseBody, MasterArray.class);
                    if (masterArray != null && masterArray.getMasters().size() > 0) {
                        result = Utils.SERVER_RESPONSE_OK;
                    } else {
                        result = "there is no service provider";
                        masterArray = null;

                    }

                } else {
                    result = "Server did not answer!";
                    masterArray = null;

                }

            } else if (response.code() == 409) {
                result = response.message();
                masterArray = null;

            } else {
                result = response.message();
                masterArray = null;

            }
        } catch (IOException e) {
            e.printStackTrace();
            result = "Connection error!";
            masterArray = null;

        }
        return masterArray;
    }

    @Override
    protected void onPostExecute(MasterArray s) {
        if (s != null) {
            delegate.onSuccess(s);
        } else {
            delegate.onFailure("Could not load from server");
        }
    }

    public interface GetAllMastersAsyncResponse {
        void onSuccess(MasterArray s);

        void onFailure(String s);

        void onUpdate();

        void onStart();
    }
}
