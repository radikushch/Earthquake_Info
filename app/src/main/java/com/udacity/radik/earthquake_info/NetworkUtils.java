package com.udacity.radik.earthquake_info;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public final class NetworkUtils {

    private NetworkUtils() {

    }

    public static String getJSONString() {
        String JSONString = "";
        String JSONUrl;
        JSONUrl = buildURL();
        try{
            if(JSONUrl != null) JSONString = loadData(JSONUrl);
        }catch(IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.e(">", JSONString);
        return JSONString;
    }

    private static String buildURL() {
        String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-01-31&minmag=6&limit=10";
        return url;
    }

    private static String loadData(String JSONUrl) throws IOException, ExecutionException, InterruptedException {
        OkHttpHandler okHttpHandler = (OkHttpHandler) new OkHttpHandler().execute(JSONUrl);
        String result = okHttpHandler.get();
        Log.e(">", "loadData: " + result);
        return result;
    }

    public static class OkHttpHandler extends AsyncTask<String , Void, String> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... strings) {
            Request request = new Request.Builder()
                    .url(strings[0])
                    .build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
