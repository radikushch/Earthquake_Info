package com.udacity.radik.earthquake_info.Model;

import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

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

    @Override
    public Loader<String> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String s) {

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
