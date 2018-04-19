package com.udacity.radik.earthquake_info.Presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.udacity.radik.earthquake_info.Model.Data.EarthQuake;
import com.udacity.radik.earthquake_info.Model.Data.Features;
import com.udacity.radik.earthquake_info.Model.Data.QueryResult;
import com.udacity.radik.earthquake_info.Model.RetrofitClient;
import com.udacity.radik.earthquake_info.View.IView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Presenter implements IMainPresenter {

    private IView view;
    private RetrofitClient retrofitClient;

    public Presenter() {
        retrofitClient = new RetrofitClient();
    }

    @Override
    public void loadData() {
        if(isNetworkAvailable()){
            showLoading();
            startLoading();
        } else {
            view.showError();
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) ((AppCompatActivity) view)
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private void startLoading() {
        Call<QueryResult> call = retrofitClient.getEarthQuakesAPI(isNetworkAvailable()).getEarthQuakes(getParameters());
                call.enqueue(new Callback<QueryResult>() {
            @Override
            public void onResponse(@NonNull Call<QueryResult> call, @NonNull Response<QueryResult> response) {
                if(response.isSuccessful()){
                    view.showData(getEarthQuakesList(response.body().getFeatures()));
                    hideLoading();
                }else {
                    view.showError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<QueryResult> call, @NonNull Throwable t) {
                view.showError();
                Log.e(">", "onFailure: ");
            }
        });
    }

    private List<EarthQuake> getEarthQuakesList(List<Features> features) {
        List<EarthQuake> list = new ArrayList<>();
        for(Features feature : features) {
            list.add(feature.getEarthQuake());
        }
        return list;
    }

    private Map<String, String> getParameters() {
        Map<String, String> parameters = new HashMap<>(4);
        parameters.put("format", "geojson");
        parameters.put("starttime", getCurrentMonth());
        parameters.put("endtime", getCurrentDate());
        parameters.put("minmag", "0");
        return parameters;

    }

    @SuppressLint("SimpleDateFormat")
    private static String getCurrentMonth() {
        return new SimpleDateFormat("yyyy-MM-01").format(new Date());
    }

    @SuppressLint("SimpleDateFormat")
    private static String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    @Override
    public void showLoading() {
        view.showLoadingIndicator();
    }

    @Override
    public void hideLoading() {
        view.hideLoadingIndicator();
    }

    @Override
    public void openSettings() {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        view.onStartActivity(intent);
    }

    @Override
    public void browseDetailInfo(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if(intent.resolveActivity(((AppCompatActivity) view).getPackageManager()) != null){
           view.onStartActivity(intent);
        }
    }

    @Override
    public void onAttachView(IView view) {
        this.view = view;
    }

    @Override
    public void onDetachView() {
        this.view = null;
    }
}
