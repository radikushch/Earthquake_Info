package com.udacity.radik.earthquake_info.Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface EarthquakeServiceAPI {

    @GET
    Call<List<EarthQuake>> getEarthQuakes(@Url String url);
}
