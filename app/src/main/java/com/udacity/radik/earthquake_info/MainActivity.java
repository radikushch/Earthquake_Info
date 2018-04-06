package com.udacity.radik.earthquake_info;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements EarthquakesAdapter.OnItemClickListener,
        android.support.v4.app.LoaderManager.LoaderCallbacks<String> {

    private static final int LOADER_ID = 0;
    private RecyclerView mList;
    private EarthquakesAdapter adapter;
    private ProgressBar mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mIndicator = findViewById(R.id.loading_indicator);
        mList = findViewById(R.id.rv_list);
        adapter = new EarthquakesAdapter(new ArrayList<EarthQuake>(), this);
        mList.setHasFixedSize(true);
        mList.setLayoutManager(new LinearLayoutManager(this));
        mList.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        connect();
    }

    private void connect() {
        if(isConnected()){
            mIndicator.setVisibility(View.VISIBLE);
            getSupportLoaderManager().initLoader(LOADER_ID, null, this).forceLoad();
        } else {
            mIndicator.setVisibility(View.GONE);
            Snackbar snackbar = Snackbar.make(findViewById(R.id.layout), "No Internet Connection",
                    Snackbar.LENGTH_LONG).setAction("Turn on", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openSettingActivity();
                }
            });
            snackbar.show();
        }
    }

    private void openSettingActivity() {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        startActivity(intent);
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public void onItemClick(URL url) {
        browseDetailInfo(url);
    }

    private void browseDetailInfo(URL url) {
        Uri uri = Uri.parse(String.valueOf(url));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }

    @Override
    public android.support.v4.content.Loader<String> onCreateLoader(int id, Bundle args) {
        return new JSONLoader(MainActivity.this);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<String> loader, String data) {
        adapter.swapData(JSONParsingUtils.parseEarthQuakeData(data));
        mIndicator.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<String> loader) {
        adapter.swapData(new ArrayList<EarthQuake>());
    }
}
