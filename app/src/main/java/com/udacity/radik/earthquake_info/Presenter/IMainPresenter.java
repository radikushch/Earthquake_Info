package com.udacity.radik.earthquake_info.Presenter;

import com.udacity.radik.earthquake_info.View.IView;

public interface IMainPresenter {
    void loadData();
    void showLoading();
    void hideLoading();
    void openSettings();
    void browseDetailInfo(String url);
    void onAttachView(IView view);
    void onDetachView();
}
