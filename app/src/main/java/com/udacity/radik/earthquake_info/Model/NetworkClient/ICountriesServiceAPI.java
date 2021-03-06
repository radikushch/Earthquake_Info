package com.udacity.radik.earthquake_info.Model.NetworkClient;

import com.udacity.radik.earthquake_info.Model.Data.Countries.Countries;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ICountriesServiceAPI {

    @GET("/countryInfoJSON?formatted=true&lang=it&username=flo_rida&style=full")
    Call<Countries> getCountriesInfo();
}
