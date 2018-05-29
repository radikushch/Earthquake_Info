package com.udacity.radik.earthquake_info.Presenter;


import com.udacity.radik.earthquake_info.View.MainActivity.IMainActivity;

public interface IMainPresenter {
    void showLoading();
    void hideLoading();
    void openSettings();
    void browseDetailInfo(String url);
    void onAttachView(IMainActivity view);
    void onDetachView();
    void openSettingsActivity();
}
