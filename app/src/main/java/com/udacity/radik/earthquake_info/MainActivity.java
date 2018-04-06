package com.udacity.radik.earthquake_info;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements EarthquakesAdapter.OnItemClickListener {

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
        String JSONString = NetworkUtils.getJSONString();
        adapter = new EarthquakesAdapter(JSONParsingUtils.parseEarthQuakeData(JSONString), this);
        mList.setHasFixedSize(true);
        mList.setLayoutManager(new LinearLayoutManager(this));
        mList.setAdapter(adapter);
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
}
