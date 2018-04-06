package com.udacity.radik.earthquake_info;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements EarthquakesAdapter.OnItemClickListener,
        android.support.v4.app.LoaderManager.LoaderCallbacks<String> {

    private static final int LOADER_ID = 0;
    private RecyclerView mList;
    private EarthquakesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mList = findViewById(R.id.rv_list);
        adapter = new EarthquakesAdapter(new ArrayList<EarthQuake>(), this);
        mList.setHasFixedSize(true);
        mList.setLayoutManager(new LinearLayoutManager(this));
        mList.setAdapter(adapter);
        getSupportLoaderManager().initLoader(LOADER_ID, null, this).forceLoad();
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
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<String> loader) {
        adapter.swapData(new ArrayList<EarthQuake>());
    }
}
