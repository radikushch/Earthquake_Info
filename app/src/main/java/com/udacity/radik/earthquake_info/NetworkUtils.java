package com.udacity.radik.earthquake_info;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import okhttp3.HttpUrl;
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
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://earthquake.usgs.gov/fdsnws/event/1/query")
                .newBuilder();
        urlBuilder.addQueryParameter("format", "geojson");
        urlBuilder.addQueryParameter("starttime", getCurrentMonth());
        urlBuilder.addQueryParameter("endtime", getCurrentDate());
        urlBuilder.addQueryParameter("minmag", "4");
        return urlBuilder.build().toString();
    }

    private static String loadData(String JSONUrl) throws IOException, ExecutionException, InterruptedException {
        OkHttpHandler okHttpHandler = (OkHttpHandler) new OkHttpHandler().execute(JSONUrl);
        return okHttpHandler.get();
    }

    @SuppressLint("SimpleDateFormat")
    private static String getCurrentMonth() {
        return new SimpleDateFormat("yyyy-MM-01").format(new Date());
    }

    @SuppressLint("SimpleDateFormat")
    private static String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
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
                if (response != null) {
                    return response.body().string();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}
