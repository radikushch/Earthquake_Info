package com.udacity.radik.earthquake_info.Presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

import com.udacity.radik.earthquake_info.Model.EarthQuake;
import com.udacity.radik.earthquake_info.Model.IModel;
import com.udacity.radik.earthquake_info.Model.Model;
import com.udacity.radik.earthquake_info.View.IView;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.HttpUrl;

public class Presenter implements IMainPresenter {

    private IView view;
    private IModel model;
    private Context context;

    public Presenter(IView view) {
        this.view = view;
        context = (Activity)view;
        model = new Model(context);
    }


    @Override
    public void loadData() {
        if(isConnected()){
            showLoading();
            startLoading();
        } else {
            view.showError();
        }
    }

    private void startLoading() {
        String url =  buildUrl();
        String jsonInfo = model.getEarthquakes(url);
        List<EarthQuake> earthQuakes = JSONParsingUtils.parseEarthQuakeData(jsonInfo);
        hideLoading();
        view.showData(earthQuakes);
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) ((AppCompatActivity) view)
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
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
    public void browseDetailInfo(URL url) {
        Uri uri = Uri.parse(String.valueOf(url));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if(intent.resolveActivity(((AppCompatActivity) view).getPackageManager()) != null){
           view.onStartActivity(intent);
        }
    }

    private String buildUrl() {
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://earthquake.usgs.gov/fdsnws/event/1/query")
                .newBuilder();
        urlBuilder.addQueryParameter("format", "geojson");
        urlBuilder.addQueryParameter("starttime", getCurrentMonth());
        urlBuilder.addQueryParameter("endtime", getCurrentDate());
        urlBuilder.addQueryParameter("minmag", "4");
        return urlBuilder.build().toString();
    }

    @SuppressLint("SimpleDateFormat")
    private static String getCurrentMonth() {
        return new SimpleDateFormat("yyyy-MM-01").format(new Date());
    }

    @SuppressLint("SimpleDateFormat")
    private static String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
}
