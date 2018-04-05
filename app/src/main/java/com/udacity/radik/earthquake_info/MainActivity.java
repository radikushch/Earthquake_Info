package com.udacity.radik.earthquake_info;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
        adapter = new EarthquakesAdapter(QueryUtils.parseEarthQuakeData(), this);
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
