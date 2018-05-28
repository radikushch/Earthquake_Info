package com.udacity.radik.earthquake_info.Model.NetworkClient;

import android.support.annotation.NonNull;

import com.udacity.radik.earthquake_info.Model.NetworkClient.ICountriesServiceAPI;
import com.udacity.radik.earthquake_info.Model.NetworkClient.IEarthquakesServiceAPI;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static ICountriesServiceAPI getCountriesInfoAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.geonames.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ICountriesServiceAPI.class);
    }


    public IEarthquakesServiceAPI getEarthQuakesAPI(boolean isNetworkAvailable, Cache cache) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(new CachingControlInterceptor(isNetworkAvailable))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://earthquake.usgs.gov/fdsnws/event/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(IEarthquakesServiceAPI.class);
    }

    private class CachingControlInterceptor implements Interceptor {

        private boolean isNetworkAvailable;

        public CachingControlInterceptor(boolean isNetworkAvailable) {
            this.isNetworkAvailable = isNetworkAvailable;
        }

        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            if (isNetworkAvailable) {
                int maxAge = getMaxAge();
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28;
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }

        private int getMaxAge() {
            int maxAge;
            Calendar c = Calendar.getInstance();
            Long now = c.getTimeInMillis();
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            c.set(Calendar.MILLISECOND, 59);
            long passed = c.getTimeInMillis() - now;
            maxAge = (int) (passed/1000);
            return maxAge;
        }
    }
}
