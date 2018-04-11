package com.udacity.radik.earthquake_info.Presenter;

import java.net.URL;

public interface IMainPresenter {
    void loadData();
    void showLoading();
    void hideLoading();
    void openSettings();
    void browseDetailInfo(URL url);
}
