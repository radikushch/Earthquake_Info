package com.udacity.radik.earthquake_info.Presenter;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.udacity.radik.earthquake_info.Model.Data.EarthQuakes.EarthQuake;
import com.udacity.radik.earthquake_info.Model.Data.EarthQuakes.Features;
import com.udacity.radik.earthquake_info.Model.Data.EarthQuakes.QueryResult;
import com.udacity.radik.earthquake_info.Model.RetrofitClient;
import com.udacity.radik.earthquake_info.Utils.NetworkUtils;
import com.udacity.radik.earthquake_info.Utils.SharedPreferencesUtils;
import com.udacity.radik.earthquake_info.View.MainActivity.IMainActivity;
import com.udacity.radik.earthquake_info.View.SettingsActivity.SettingsActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Cache;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements IMainPresenter, LifecycleObserver {

    private IMainActivity view;
    private RetrofitClient retrofitClient;

    public MainPresenter() {
        retrofitClient = new RetrofitClient();
    }


    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void loadData() {
        showLoading();
        final Cache cache = new Cache(
                new File(((AppCompatActivity) view).getCacheDir(), "http"),
                250000000);

        boolean isConnectToNetwork = NetworkUtils.isNetworkAvailable((AppCompatActivity) view);
        Map<String, String> queryParameters = SharedPreferencesUtils
                .getQueryParameters((AppCompatActivity) view);

        Call<QueryResult> call = retrofitClient
                .getEarthQuakesAPI(isConnectToNetwork, cache)
                .getEarthQuakes(queryParameters);
        call.enqueue(new Callback<QueryResult>() {
            @Override
            public void onResponse(@NonNull Call<QueryResult> call, @NonNull Response<QueryResult> response) {
                if(response.isSuccessful()){
                    if (response.body() != null & response.body().getFeatures() != null) {
                        view.showData(getEarthQuakesList(response.body().getFeatures()));
                    }
                    hideLoading();
                }else {
                    view.showError();
                    try {
                        Log.e("load", "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<QueryResult> call, @NonNull Throwable t) {
                view.showError();
                Log.e("load", "onFailure: " + t.getMessage());
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
    public void onAttachView(IMainActivity view) {
        this.view = view;
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDetachView() {
        this.view = null;
    }

    @Override
    public void openSettingsActivity() {
        Intent intent = new Intent((AppCompatActivity)view, SettingsActivity.class);
        view.onStartActivity(intent);
    }

}
