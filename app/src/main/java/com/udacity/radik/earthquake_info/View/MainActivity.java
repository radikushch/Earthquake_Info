package com.udacity.radik.earthquake_info.View;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.udacity.radik.earthquake_info.Model.EarthQuake;
import com.udacity.radik.earthquake_info.EarthquakesAdapter;
import com.udacity.radik.earthquake_info.Presenter.IMainPresenter;
import com.udacity.radik.earthquake_info.Presenter.Presenter;
import com.udacity.radik.earthquake_info.R;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IView, EarthquakesAdapter.OnItemClickListener {

    private RecyclerView mList;
    private EarthquakesAdapter adapter;
    private ProgressBar mLoadingIndicator;
    private IMainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mLoadingIndicator = findViewById(R.id.loading_indicator);
        mList = findViewById(R.id.rv_list);
        adapter = new EarthquakesAdapter(new ArrayList<EarthQuake>(), this);
        mList.setHasFixedSize(true);
        mList.setLayoutManager(new LinearLayoutManager(this));
        mList.setAdapter(adapter);
        presenter = new Presenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadData();
    }

    @Override
    public void showData(List<EarthQuake> earthQuakes) {
        adapter.swapData(earthQuakes);
    }

    @Override
    public void showLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.layout), "No Internet Connection",
                Snackbar.LENGTH_LONG).setAction("Turn on", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.openSettings();
            }
        });
        snackbar.show();
    }

    @Override
    public void onStartActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void onItemClick(URL url) {
        showDetailInfo(url);
    }

    @Override
    public void showDetailInfo(URL url) {
        presenter.browseDetailInfo(url);
    }
}
