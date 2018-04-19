package com.udacity.radik.earthquake_info.View;

import android.content.Intent;

import com.udacity.radik.earthquake_info.Model.Data.EarthQuake;

import java.net.URL;
import java.util.List;

public interface IView {
    void showData(List<EarthQuake> earthQuakes);
    void showLoadingIndicator();
    void hideLoadingIndicator();
    void showError();
    void showDetailInfo(String url);
    void onStartActivity(Intent intent);

}
