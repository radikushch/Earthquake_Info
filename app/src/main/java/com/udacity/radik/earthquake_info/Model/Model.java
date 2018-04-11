package com.udacity.radik.earthquake_info.Model;

import android.content.Context;

public class Model implements IModel {

    private Context context;

    public Model(Context context) {
        this.context = context;
    }

    @Override
    public String getEarthquakes(String url) {
        String result = getJSONString(url);
        return result;
    }

    public static String getJSONString(String url) {
        String jsonResult = "";
        return jsonResult;
    }
}
