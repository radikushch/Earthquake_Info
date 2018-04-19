package com.udacity.radik.earthquake_info.Model;

import com.udacity.radik.earthquake_info.Model.Data.QueryResult;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface IEarthquakeServiceAPI {

    @GET("/fdsnws/event/1/query")
    Call<QueryResult> getEarthQuakes(@QueryMap Map<String, String> parameters);
}
