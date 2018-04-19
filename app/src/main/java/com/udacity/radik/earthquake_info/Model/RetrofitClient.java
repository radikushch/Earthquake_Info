package com.udacity.radik.earthquake_info.Model;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public IEarthquakeServiceAPI getEarthQuakesAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://earthquake.usgs.gov/fdsnws/event/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(IEarthquakeServiceAPI.class);
    }
}
