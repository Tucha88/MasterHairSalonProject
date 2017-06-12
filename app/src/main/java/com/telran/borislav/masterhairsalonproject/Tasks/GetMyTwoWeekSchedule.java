package com.telran.borislav.masterhairsalonproject.Tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Response;
import com.telran.borislav.masterhairsalonproject.Models.CalendarDayCustom;
import com.telran.borislav.masterhairsalonproject.Models.Provider;
import com.telran.borislav.masterhairsalonproject.Utilitis.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boris on 11.06.2017.
 */

public class GetMyTwoWeekSchedule extends AsyncTask<Void, Void, String> {
    public ScheduleAsyncResponse delegate;
    private String token;
    private String path;
    private Context context;


    public GetMyTwoWeekSchedule(ScheduleAsyncResponse delegate, String token, String path, Context context) {
        this.delegate = delegate;
        this.token = token;
        this.path = path;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        delegate.processUpdate();
    }

    @Override
    protected String doInBackground(Void... voids) {
        String result = Utils.SERVER_RESPONSE_OK;
        Gson gson = new Gson();
        try {
            Response response = Provider.getInstance().get(path, token);
            if (response.code() < 400) {
                String responseBody = response.body().string();
                if (!responseBody.isEmpty()) {
                    TypeToken<List<CalendarDayCustom>> typeToken = new TypeToken<List<CalendarDayCustom>>() {
                    };
                    ArrayList<CalendarDayCustom> calendarDays = gson.fromJson(responseBody, typeToken.getType());
                    SharedPreferences sharedPreferences = context.getSharedPreferences(Utils.PROFILE, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Utils.CALENDAR_DAYS, gson.toJson(calendarDays, typeToken.getType()));
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
        if (string.equals(Utils.SERVER_RESPONSE_OK)) {
            delegate.processFinishTwoWeeks();
        } else {
            delegate.profileGetErrorTwoWeeks(string);
        }


    }

    public interface ScheduleAsyncResponse {
        void processFinishTwoWeeks();

        void processUpdate();
        void profileGetErrorTwoWeeks(String s);
    }
}

