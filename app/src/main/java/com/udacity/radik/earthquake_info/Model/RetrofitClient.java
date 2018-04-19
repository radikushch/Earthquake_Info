package com.udacity.radik.earthquake_info.Model;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import lombok.experimental.UtilityClass;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {



    public IEarthquakeServiceAPI getEarthQuakesAPI(boolean isNetworkAvailable) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new ResponseCacheInterceptor())
                .addInterceptor(new OfflineResponseCacheInterceptor(isNetworkAvailable))
                .cache(new Cache(new File("Cache"), 5 * 1024 * 1024))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                //.client(okHttpClient)
                .baseUrl("https://earthquake.usgs.gov/fdsnws/event/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(IEarthquakeServiceAPI.class);
    }

    private static class ResponseCacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            int maxAge = getMaxAge();
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        }

        public int getMaxAge() {
            //get time to the end of current day
            return 120;
        }
    }

    private static class OfflineResponseCacheInterceptor implements Interceptor {

        private boolean isNetworkAvailable;

        public OfflineResponseCacheInterceptor(boolean isNetworkAvailable) {
            this.isNetworkAvailable = isNetworkAvailable;
        }

        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request request = chain.request();
            if(isNetworkAvailable) {
                request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale" + 604800)
                        .build();
            }
            return chain.proceed(request);
        }
    }
}
